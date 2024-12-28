package com.logging.shortenurl.presentation;

import com.logging.shortenurl.domain.LackOfShortenUrlKeyException;
import com.logging.shortenurl.domain.NotFoundShortenUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LackOfShortenUrlKeyException.class)
    public ResponseEntity<String> handleLackOfShortenUrlKeyException(
            LackOfShortenUrlKeyException e
    ) {
        log.error("단축 URL 자원이 부족합니다.");
        return new ResponseEntity<>("단축 URL 자원이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundShortenUrlException.class)
    public ResponseEntity<String> handleNotFoundShortenUrlException(
            NotFoundShortenUrlException ex
    ) {
        // 사용자가 잘못 입력한 부분이라 이 부분에서는 info 레벨로 작성하는 게 맞음.
        // 반면, 이 URL을 보낸 게 사용자가 아니라 시스템끼리일 때에는 개발자가 처리해야 하는 일이므로 그 떈 ERROR로 찍는 게 맞음.
        log.info(ex.getMessage());
        return new ResponseEntity<>("단축 URL을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        StringBuilder errorMessage = new StringBuilder("유효성 검증 실패: ");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(String.format("필드 '%s': %s. ", error.getField(), error.getDefaultMessage()));
        });

        log.warn("잘못된 요청: {}", errorMessage);

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }
}
