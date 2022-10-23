package com.a606.api.controller;

import com.a606.api.service.GameService;
import com.a606.common.util.SudalUserDetails;
import com.a606.db.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "게임 API", tags = {"Game"})
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private final GameService gameService;

    @PutMapping("game/point/{point}")
    public ResponseEntity<Long> updateGamePoint(@ApiIgnore Authentication authentication, @PathVariable long point) {
        if (authentication == null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();

        return new ResponseEntity<Long>(gameService.updateGamePointById(user.getId(), point),HttpStatus.OK);
    }
}
