CREATE  OR REPLACE FUNCTION
    get_default_photo_uuid()
    RETURNS UUID AS $$
DECLARE
default_photo_id    UUID;
BEGIN
SELECT id  INTO default_photo_id
FROM primary_user_photo
WHERE is_default = true LIMIT 1;

IF default_photo_id IS NULL THEN
        RAISE EXCEPTION 'can not found default photo' USING ERRCODE = 'P0001';
END IF;

RETURN default_photo_id;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;

CREATE TABLE IF NOT EXISTS primary_user_photo(
    id          UUID    DEFAULT gen_random_uuid() PRIMARY KEY,
    key         VARCHAR(255)    NOT NULL,
    is_default  BOOLEAN NOT NULL DEFAULT FALSE,
    uploaded_at TIMESTAMP   NOT NULL DEFAULT current_timestamp
    );

CREATE UNIQUE INDEX key_undx ON primary_user_photo(key);

INSERT INTO primary_user_photo (key, is_default) VALUES ('6178abb3-7f12-9960-36b3-d49d025ebfa7.png', true);

CREATE TABLE IF NOT EXISTS primary_user(
                                           id                      BIGINT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                           keycloak_user_id          VARCHAR(255)          NOT NULL,
    primary_user_photo_id   UUID                    NOT NULL DEFAULT get_default_photo_uuid(),
    FOREIGN KEY(primary_user_photo_id) REFERENCES primary_user_photo(id)
    ON UPDATE CASCADE
    ON DELETE SET DEFAULT
    );

CREATE UNIQUE INDEX keycloak_user_id_undx ON primary_user(keycloak_user_id);

CREATE TABLE IF NOT EXISTS photo(
    id          UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    keys        VARCHAR(255)    NOT NULL,
    is_default  BOOLEAN         NOT NULL    DEFAULT FALSE,
    uploaded_at TIMESTAMP       NOT NULL    DEFAULT current_timestamp
    );

CREATE TABLE IF NOT EXISTS primary_category(
                                               id          BIGINT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                               name        VARCHAR(255)    NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS sub_category(
                                           id          BIGINT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                           name        VARCHAR(255)    NOT NULL UNIQUE,
    attributes  JSONB   NOT NULL,
    primary_category_id     BIGINT  NOT NULL,
    FOREIGN KEY (primary_category_id) REFERENCES primary_category(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS advertisement(
    id                  UUID PRIMARY KEY    DEFAULT gen_random_uuid(),
    name                VARCHAR(255)        NOT NULL    UNIQUE,
    location            VARCHAR(255)        NOT NULL,
    seat                SMALLINT            NOT NULL,
    price_per_person    NUMERIC(1000, 0)    NOT NULL,
    description         VARCHAR(500)        NOT NULL,
    supply              VARCHAR(500)        NOT NULL,
    with_delivery       BOOLEAN             NOT NULL,
    is_one_day          BOOLEAN             NOT NULL,
    is_multi_day        BOOLEAN             NOT NULL,
    is_activated        BOOLEAN             NOT NULL        DEFAULT false,
    sub_category_id     BIGINT              NOT NULL,
    primary_user_id     BIGINT              NOT NULL,
    FOREIGN KEY (sub_category_id) REFERENCES sub_category(id)
    ON DELETE CASCADE,
    FOREIGN KEY (primary_user_id) REFERENCES primary_user(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS advertisement_photo(
                                                  id      BIGINT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                                  advertisement_id    UUID    NOT NULL,
                                                  photo_id            UUID    NOT NULL,
                                                  FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES photo(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS favorites(
                                        id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        advertisement_id   UUID NOT NULL,
                                        FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS advertisement_order(
                                                  id                          BIGINT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                                  expires_at                  TIMESTAMP           NOT NULL    DEFAULT (current_timestamp + INTERVAL '1 day'),
    order_status                VARCHAR(50)         NOT NULL,
    seat                        SMALLINT            NOT NULL,
    arrival                     TIMESTAMP           NOT NULL,
    departure                   TIMESTAMP           NOT NULL,
    advertisement_id            UUID                NOT NULL    UNIQUE,
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS notification(
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_type   VARCHAR(100)    NOT NULL,
    attributes          JSONB,
    primary_user_id     BIGINT          NOT NULL,
    pushed_at           TIMESTAMP   DEFAULT current_timestamp,
    FOREIGN KEY (primary_user_id) REFERENCES primary_user(id)
    ON DELETE CASCADE
    ); --todo make cron job with deletion of notifications

CREATE TABLE IF NOT EXISTS comment(
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    review                  VARCHAR(500)    NOT NULL,
    uploaded_at             TIMESTAMP       NOT NULL    DEFAULT current_timestamp,
    rating                  SMALLINT        NOT NULL,
    primary_user_id         BIGINT          NOT NULL,
    FOREIGN KEY (primary_user_id) REFERENCES primary_user(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS advertisement_comment(
    id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    advertisement_id    UUID    NOT NULL,
    comment_id          UUID    NOT NULL UNIQUE,
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id)
    ON DELETE CASCADE,
    FOREIGN KEY (comment_id) REFERENCES comment(id)
    ON DELETE SET NULL
    );

CREATE OR REPLACE FUNCTION
    check_advertisement_photo_amount()
    RETURNS trigger AS $$
DECLARE
photo_counter    SMALLINT;
BEGIN
SELECT COUNT(advertisement_id) INTO photo_counter
FROM advertisement_photo
WHERE advertisement_id = OLD.advertisement_id;

IF photo_counter = 1 THEN
        INSERT INTO advertisement_photo (advertisement_id, photo_id)
        VALUES (OLD.advertisement_id, (SELECT id FROM photo
                                       WHERE is_default = true LIMIT 1));

        RAISE NOTICE 'advertisement % photos were set to default', OLD.advertisement_id;

RETURN OLD;
ELSEIF photo_counter > 1 THEN
        RETURN OLD;
ELSE
        RAISE EXCEPTION 'photo counter of % have % rows',
            OLD.advertisement_id, photo_counter USING ERRCODE = 'P0001';
END IF;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;

CREATE OR REPLACE TRIGGER check_advertisement_photo_amount
    BEFORE DELETE ON advertisement_photo FOR EACH ROW
EXECUTE PROCEDURE check_advertisement_photo_amount();

CREATE OR REPLACE FUNCTION
    check_default_and_amount_advertisement_photo()
    RETURNS trigger AS $$
DECLARE
is_exists           boolean;
    default_photo_id    uuid;
    photo_counter       smallint;
    old_photo_counter   smallint;
BEGIN
SELECT id INTO default_photo_id
FROM photo
WHERE is_default = true LIMIT 1;

SELECT EXISTS(
               SELECT advertisement_id FROM advertisement_photo
               WHERE advertisement_id = NEW.advertisement_id AND
                       photo_id = default_photo_id
           ) INTO is_exists;

SELECT COUNT(advertisement_id) INTO photo_counter
FROM advertisement_photo
WHERE advertisement_id = NEW.advertisement_id;

IF is_exists AND photo_counter > 1 THEN
DELETE FROM advertisement_photo
WHERE advertisement_id = NEW.advertisement_id AND
        photo_id = default_photo_id;
RAISE NOTICE 'default photo of % have been deleted', NEW.advertisement_id;
END IF;

    old_photo_counter := photo_counter - 1;

    IF old_photo_counter = 5 THEN
        RAISE EXCEPTION '% photos hit a limit of 5', NEW.advertisement_id USING ERRCODE = 'P0001';
END IF;

RETURN null;
END;
$$ LANGUAGE plpgsql
    SECURITY DEFINER;

CREATE OR REPLACE TRIGGER check_default_advertisement_photo
    AFTER INSERT ON advertisement_photo FOR EACH ROW
EXECUTE PROCEDURE check_default_and_amount_advertisement_photo();

--SELECT cron.schedule('* */23 * * *',$$DELETE FROM notification WHERE pushed_at >= current_timestamp + interval '30 DAYS'$$);
--SELECT cron.schedule('* */1 * * *', $$DELETE FROM advertisement_order WHERE expires_at <= current_timestamp AND order_status = 'BOOKED'$$);
--UPDATE cron.job SET nodename='';