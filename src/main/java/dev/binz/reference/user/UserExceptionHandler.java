package dev.binz.reference.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "dev.binz.reference.user")
public class UserExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleConstraintViolationException(ConstraintViolationException ex) {
    	log.debug("============");
        return ex.getConstraintViolations().stream()
                .map(violation -> "필드: " + violation.getPropertyPath() + " 오류: " + violation.getMessage())
                .collect(Collectors.toList());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        log.debug("============");
        // BindingResult에서 오류 정보 추출
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // 필드명
            String message = error.getDefaultMessage(); // 오류 메시지
            errors.put(fieldName, message); // 필드명과 오류 메시지 맵에 추가
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(Exception ex) {
    	log.debug("============");
    	return ex.getClass().getSimpleName();
    }
}
