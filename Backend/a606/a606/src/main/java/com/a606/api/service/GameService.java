package com.a606.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface GameService {

    @Transactional
    long updateGamePointById(long userId, long gamePoint);
}
