package dev.spring.security.oauth2.exception;

import dev.spring.security.oauth2.dto.exception.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseDto<?> handlerArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseDto<?> handlerArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseDto<?> handlerCustomException(CustomException e) {
        log.error("CustomException", e);
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseDto<?> handlerException(Exception e) {
        log.error("Exception", e);
        return ResponseDto.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
