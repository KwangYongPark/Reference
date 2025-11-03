package dev.binz.reference.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * HTTP 요청을 가로채어 로깅하는 필터
 */
@Slf4j
@Component
public class CustomFilter implements Filter {
    
    /**
     * 필터 초기화 시 호출되는 메서드
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("CustomFilter 초기화됨");
    }

    /**
     * HTTP 요청을 가로채어 필터링하는 메서드
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("[CustomFilter] 요청 필터링 시작");
        chain.doFilter(request, response);
        log.debug("[CustomFilter] 요청 필터링 완료");
    }

    /**
     * 필터 종료 시 호출되는 메서드
     */
    @Override
    public void destroy() {
        log.debug("CustomFilter 종료됨");
    }
}
