package dev.binz.reference.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 요청을 가로채어 유효성을 검사하는 인터셉터
 */
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {

    /**
     * 요청을 처리하기 전에 유효성 검사 수행
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            log.warn("[CustomInterceptor] 요청 차단 - name 파라미터 누락");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        log.debug("[CustomInterceptor] 요청 통과");
        return true;
    }
}
