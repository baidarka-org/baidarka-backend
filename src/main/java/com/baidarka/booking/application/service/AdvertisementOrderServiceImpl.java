package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.domain.order.repository.AdvertisementOrderRepository;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.interfaces.facade.AdvertisementOrderInsertionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementOrderServiceImpl implements AdvertisementOrderService {
    private final AdvertisementOrderInsertionFacade facade;
    private final AdvertisementOrderRepository repository;

    @Override
    public void save(AdvertisementOrderProjection advertisementOrder) {
        facade.insert(advertisementOrder);
    }

    @Override
    public Integer getFreeSeatBy(LocalDateTime date, UUID advertisementId) {
        return repository.findFreeSeatBy(date, advertisementId);
    }
}
