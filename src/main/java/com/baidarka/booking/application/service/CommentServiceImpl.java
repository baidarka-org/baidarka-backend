package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.comment.projection.CommentProjection;
import com.baidarka.booking.domain.comment.repository.CommentRepository;
import com.baidarka.booking.domain.comment.service.CommentService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    @Override
    @Transactional(
            propagation = REQUIRES_NEW,
            rollbackFor = DataAccessException.class)
    public void save(CommentProjection comment) {
        try {
            repository.insert(comment);
        } catch (DataAccessException dae) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public List<CommentProjection> getBy(UUID advertisementId) {
        return repository.findByAdvertisementId(advertisementId); //todo return firstname and lastname of commenattotr
    }
}
