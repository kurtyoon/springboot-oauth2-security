package dev.spring.security.oauth2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 400: Bad Request
     */
    BAD_REQUEST_ERROR("4000", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_ARGUMENT("4001", HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    MISSING_REQUEST_PARAMETER("4002", HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),

    /**
     * 401: Unauthorized
     */
    UNAUTHORIZED_ERROR("4010", HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    /**
     * 403: Forbidden
     */
    FORBIDDEN_ERROR("4030", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    /**
     * 404: Not Found
     */
    NOT_FOUND_ERROR("4040", HttpStatus.NOT_FOUND, "요청하신 페이지를 찾을 수 없습니다."),

    /**
     * 405: Method Not Allowed
     */
    METHOD_NOT_ALLOWED_ERROR("4050", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP Method 입니다."),

    /**
     * 500: Internal Server Error
     */
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;
}
