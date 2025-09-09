package BITAmin.BE.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, 402, "해당 사용자를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "권한이 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "리소스를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, 405, "비밀번호가 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
