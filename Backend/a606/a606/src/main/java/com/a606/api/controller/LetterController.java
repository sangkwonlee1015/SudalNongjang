package com.a606.api.controller;

import com.a606.api.dto.LetterDto;
import com.a606.api.service.LetterService;
import com.a606.common.util.SudalUserDetails;
import com.a606.db.entity.Letter;
import com.a606.db.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(value = "알림 API", tags = {"Letter"})
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class LetterController {
    @Autowired
    LetterService letterService;

    @GetMapping("letter")
    public ResponseEntity<List<LetterDto>> getLetters(@ApiIgnore Authentication authentication) {
        if (authentication == null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();

        return new ResponseEntity<List<LetterDto>>(letterService.getAllLetters(user), HttpStatus.OK);
    }

    @GetMapping("letter/{letterId}")
    public void readLetter(@PathVariable long letterId) {
       letterService.readLetter(letterId);
    }
}
