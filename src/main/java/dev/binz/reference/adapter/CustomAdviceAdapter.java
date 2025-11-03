package dev.binz.reference.adapter;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTP 요청을 가로채어 로깅하는 어댑터
 */
@Slf4j
@RestControllerAdvice(basePackages = {"dev.binz.reference.user.controller"})
public class CustomAdviceAdapter extends RequestBodyAdviceAdapter {

    /**
     * 요청 본문을 처리할 대상을 결정하는 메서드
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("CustomAdviceAdapter: supports() 호출됨 - 메서드: {}, 대상 타입: {}", 
                methodParameter.getMethod().getName(), targetType.getTypeName());
        return true;
    }
    
    /**
     * 요청 본문을 처리한 후 추가적인 로깅을 수행하는 메서드
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("CustomAdviceAdapter: afterBodyRead() 호출됨 - 요청 본문 처리 완료, 대상 타입: {}", targetType.getTypeName());
        return body;
    }

}
