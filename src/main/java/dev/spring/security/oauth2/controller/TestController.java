package dev.spring.security.oauth2.controller;

import dev.spring.security.oauth2.annotation.UserId;
import dev.spring.security.oauth2.dto.exception.ResponseDto;
import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/test")
    public ResponseDto<?> test(@RequestParam("id") String id) {
//        return ResponseDto.fail(new CustomException(ErrorCode.NOT_FOUND_ERROR));
        return ResponseDto.ok(id);
    }

    @GetMapping("/test2")
    public ResponseDto<?> testUser(@UserId String userId) {
        return ResponseDto.ok(userId);
    }
}
