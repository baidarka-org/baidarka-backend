CREATE FUNCTION
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