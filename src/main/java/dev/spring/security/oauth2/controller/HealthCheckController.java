package dev.spring.security.oauth2.controller;

import dev.spring.security.oauth2.dto.exception.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseDto<?> healthCheck() {
        return ResponseDto.ok("health check");
    }
}
