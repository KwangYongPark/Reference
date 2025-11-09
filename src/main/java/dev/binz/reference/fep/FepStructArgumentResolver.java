package dev.binz.reference.fep;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.binz.reference.fep.dto.AbstractFepStruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.stream.Collectors;

/**
 * Spring MVC의 요청 인자 처리 흐름을 가로채어(Override) FEP 전용 동적 DTO 변환을 수행하는 클래스입니다.
 * Controller 메서드의 인자가 AbstractFepStruct 타입일 때만 작동하도록 설계되었습니다.
 */
@Component // 💡 Spring Bean으로 등록하여 DI(의존성 주입)가 가능하도록 합니다.
public class FepStructArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper fepObjectMapper;

    /**
     * 생성자 주입(Constructor Injection)을 통해 FEP 전용 ObjectMapper를 주입받습니다.
     * 이 ObjectMapper는 FepJacksonConfig에서 등록되었으며, FepRequestDeserializer(동적 역직렬화 로직)가 연결되어 있습니다.
     *
     * @param fepObjectMapper FEP 전용 설정이 적용된 ObjectMapper Bean
     */
    public FepStructArgumentResolver(@Qualifier("fepObjectMapper") ObjectMapper fepObjectMapper) {
        // @Qualifier를 사용하여 Spring Context에 여러 개 있을 수 있는 ObjectMapper 중 "fepObjectMapper" Bean을 특정합니다.
        this.fepObjectMapper = fepObjectMapper;
    }

    /**
     * 현재 Resolver가 Controller 메서드의 인자를 처리할 수 있는지 여부를 결정합니다.
     * 이 메서드가 true를 반환해야만 resolveArgument가 호출됩니다.
     *
     * @param parameter Controller 메서드의 인자 메타데이터 (타입, 어노테이션 정보 포함)
     * @return 인자를 처리할 수 있으면 true, 아니면 false
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 1. 인자에 @RequestBody 어노테이션이 붙어 있는지 확인 (JSON 요청인지 확인)
        boolean isRequestBody = parameter.hasParameterAnnotation(RequestBody.class);

        // 2. 인자 타입이 AbstractFepStruct<?>의 자식 클래스인지 확인 (FEP DTO인지 확인)
        boolean isFepStruct = AbstractFepStruct.class.isAssignableFrom(parameter.getParameterType());

        // 🚀 @RequestBody가 붙었고, 타입이 FEP의 추상 DTO일 때만 이 Resolver가 기본 처리기를 가로챕니다.
        return isRequestBody && isFepStruct;
    }

    /**
     * supportsParameter가 true를 반환했을 때 실제로 인자(DTO 객체)를 생성하고 반환합니다.
     * 이 메서드의 반환 값이 Controller 메서드의 인자로 주입됩니다.
     *
     * @return JSON 데이터를 동적 변환한 최종 DTO 객체 (FepRequestDto 또는 FepRequest2Dto 등)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 1. 요청 본문(JSON) 전체를 문자열로 읽습니다.
        //    일반적인 @RequestBody 처럼 InputStream을 한 번 읽으면 재사용할 수 없으므로,
        //    여기에 직접 JSON을 읽는 로직을 구현합니다.
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = servletRequest.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        // 2. FEP 전용 ObjectMapper를 사용하여 동적 변환을 수행합니다.
        //    targetType은 AbstractFepStruct.class이지만,
        //    fepObjectMapper에 등록된 FepRequestDeserializer가 JSON의 ID를 확인하여
        //    실제 구체 클래스(FepRequestDto 등)로 변환을 수행합니다.
        Class<?> targetType = parameter.getParameterType(); // AbstractFepStruct.class

        // 🚀 커스텀 로직의 핵심: FEP 전용 Deserializer가 작동하는 ObjectMapper로 DTO를 최종 생성합니다.
        return fepObjectMapper.readValue(jsonBody, targetType);
    }
}