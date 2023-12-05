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
    BAD_REQUEST_ERROR(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_ARGUMENT(400, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    MISSING_REQUEST_PARAMETER(400, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),

    /**
     * 401: Unauthorized
     */
    UNAUTHORIZED_ERROR(401, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    /**
     * 403: Forbidden
     */
    FORBIDDEN_ERROR(403, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    /**
     * 404: Not Found
     */
    NOT_FOUND_ERROR(404, HttpStatus.NOT_FOUND, "요청하신 페이지를 찾을 수 없습니다."),

    /**
     * 405: Method Not Allowed
     */
    METHOD_NOT_ALLOWED_ERROR(405, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP Method 입니다."),

    /**
     * 500: Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.");


    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
