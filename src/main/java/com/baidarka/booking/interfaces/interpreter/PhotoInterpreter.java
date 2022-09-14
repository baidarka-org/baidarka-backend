package com.baidarka.booking.interfaces.interpreter;

import com.baidarka.booking.domain.photo.service.PhotoService;
import com.baidarka.booking.infrastructure.model.PhotoType;
import org.springframework.stereotype.Component;

@Component
public class PhotoInterpreter {

    public PhotoService doTask(PhotoType photoType) {
        return photoType.get();
    }
}
