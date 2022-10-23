package com.a606.api.service;

import com.a606.api.dto.LetterDto;
import com.a606.db.entity.Letter;
import com.a606.db.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LetterService {
    void createLetter(User user, String msg);
    void readLetter(long letterId);
    List<LetterDto> getAllLetters(User user);
}
