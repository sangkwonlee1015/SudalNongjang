package com.a606.api.service;

import com.a606.db.entity.User;
import com.a606.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService{
    @Autowired
    UserRepository userRepository;


    public long updateGamePointById(long userId, long gamePoint) {
        User user = userRepository.findById(userId).get();
        user.setGamePoint(user.getGamePoint() + gamePoint);
        userRepository.save(user);
        return user.getGamePoint();
    }
}
