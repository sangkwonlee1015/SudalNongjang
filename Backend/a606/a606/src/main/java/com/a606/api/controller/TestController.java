package com.a606.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "Test API", tags = {"Test"})
@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {

    @PostMapping("/")
    @ApiOperation(value = "Test", notes = "Test용")
    public String test() {
        return "Test";
    }
}
