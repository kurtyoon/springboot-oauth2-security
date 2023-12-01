package dev.spring.security.oauth2.dto.exception;

import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Getter
public class ResponseDto<T> {
    @JsonIgnore
    private HttpStatus httpStatus;
    private final Boolean success;
    private final T data;
    private ExceptionDto error;

    public ResponseDto(final HttpStatus httpStatus, final Boolean success,
                       @Nullable final T data, final ExceptionDto error) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ResponseDto<T> ok(final T data) {
        return new ResponseDto<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ResponseDto<T> created(final T data) {
        return new ResponseDto<>(HttpStatus.CREATED, true, data, null);
    }

    public static ResponseDto<Object> fail(final ConstraintViolationException e) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, new ArgumentNotValidExceptionDto(e));
    }

    public static ResponseDto<Object> fail(final MethodArgumentNotValidException e) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, new ArgumentNotValidExceptionDto(e));
    }

    public static ResponseDto<Object> fail(final MissingServletRequestParameterException e) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, new ExceptionDto(ErrorCode.MISSING_REQUEST_PARAMETER));
    }

    public static ResponseDto<Object> fail(final CustomException e) {
        return new ResponseDto<>(e.getErrorCode().getStatus(), false, null, new ExceptionDto(e.getErrorCode()));
    }
}
