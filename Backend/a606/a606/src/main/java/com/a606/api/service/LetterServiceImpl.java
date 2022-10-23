package com.a606.api.service;

import com.a606.api.dto.LetterDto;
import com.a606.db.entity.Letter;
import com.a606.db.entity.User;
import com.a606.db.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService{
    @Autowired
    LetterRepository letterRepository;

    @Override
    public void createLetter(User user, String msg) {
        Letter letter = new Letter();
        letter.setUser(user);
        letter.setMsg(msg);
        letter.setRead(false);
        letterRepository.save(letter);
    }

    @Override
    public void readLetter(long letterId) {
        Letter letter = letterRepository.findById(letterId).get();
        letter.setRead(true);
        letterRepository.save(letter);
    }

    @Override
    public List<LetterDto> getAllLetters(User user) {
        List<Letter> lists = letterRepository.findAll();
        List<LetterDto> list = new ArrayList<>();

        for(Letter l : lists) {
            if(user.equals(l.getUser())) {
                LetterDto letterDto = new LetterDto();
                letterDto.setId(l.getId());
                letterDto.setUserId(user.getId());
                letterDto.setMsg(l.getMsg());
                letterDto.setDate(l.getDate());
                letterDto.setRead(l.isRead());

                list.add(letterDto);
            }
        }

        return list;
    }
}
