package dev.binz.reference.fep;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring Web MVC 설정을 커스터마이징 하는 클래스입니다.
 * FEP 요청을 위한 커스텀 인자 처리기(Argument Resolver)를 Spring MVC 파이프라인에 등록합니다.
 */
@Configuration // 💡 설정 클래스로 등록: 이 클래스가 Bean 정의를 포함하고 있음을 Spring에게 알립니다.
public class FepConfig implements WebMvcConfigurer { // 💡 WebMvcConfigurer 구현: MVC 설정을 확장/재정의 할 수 있도록 합니다.

    // 💡 FEP 요청 처리의 핵심 로직을 담고 있는 Argument Resolver입니다.
    private final FepStructArgumentResolver fepResolver;

    /**
     * 생성자 주입(Constructor Injection)을 통해 FepStructArgumentResolver Bean을 주입받습니다.
     * 이 Resolver는 @Component 어노테이션 덕분에 Spring에 의해 자동으로 생성됩니다.
     *
     * @param fepResolver 주입받을 FEP 전용 인자 처리기
     */
    public FepConfig(FepStructArgumentResolver fepResolver) {
        this.fepResolver = fepResolver;
    }

    /**
     * Controller 메서드의 인자를 처리할 커스텀 Resolver들을 등록합니다.
     * WebMvcConfigurer 인터페이스의 오버라이드 메서드입니다.
     *
     * @param resolvers 현재 등록된 모든 HandlerMethodArgumentResolver 목록
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 🚀 핵심 로직: FEP 전용 Resolver를 목록의 가장 앞에(인덱스 0) 추가합니다.
        // Spring MVC는 요청이 들어오면 이 목록을 순서대로 순회하며 인자를 처리할 수 있는 Resolver를 찾습니다.
        // resolvers.add(0, fepResolver);

        // 💡 우선순위 확보: FEP Resolver가 기본 @RequestBody 처리기(RequestResponseBodyMethodProcessor)보다
        // 먼저 실행되어 AbstractFepStruct<?> 타입의 인자를 가로채 처리하게 됩니다.
        resolvers.add(0, fepResolver);
    }
}