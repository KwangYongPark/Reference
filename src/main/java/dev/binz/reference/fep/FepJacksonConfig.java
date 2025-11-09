package dev.binz.reference.fep;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.binz.reference.fep.dto.AbstractFepStruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FEP(Front-End Processor) 요청 처리를 위한 Jackson ObjectMapper 설정을 담당하는 Configuration 클래스입니다.
 * FEP 전용 동적 역직렬화 로직(FepRequestDeserializer)을 표준 ObjectMapper에 연결합니다.
 */
@Configuration // 💡 설정 클래스로 등록: Spring이 이 클래스를 읽어 Bean 정의를 찾습니다.
public class FepJacksonConfig {

    /**
     * FEP 전용 설정이 적용된 ObjectMapper Bean을 생성합니다.
     * 이 Bean은 @Qualifier("fepObjectMapper")를 통해 FepStructArgumentResolver에 주입됩니다.
     */
    @Bean("fepObjectMapper") // Bean 이름을 명확히 지정하여 @Qualifier에 대응
    public ObjectMapper fepObjectMapper(MessageTypeRegistry registry) {

        // 1. 기본 ObjectMapper 인스턴스 생성:
        //    Spring이 제공하는 기본 Jackson 설정(날짜 포맷 등)을 따르는 인스턴스입니다.
        ObjectMapper mapper = new ObjectMapper();

        // 2. SimpleModule 생성:
        //    Jackson 모듈은 표준 Jackson 기능에 커스텀 기능(Deserializer, Serializer)을 추가하기 위한 확장 지점입니다.
        //    FEP 요청을 처리하는 커스텀 로직을 묶어 등록하기 위해 이 모듈을 사용합니다.
        SimpleModule module = new SimpleModule("FepRequestModule", Version.unknownVersion());

        // 3. 🚀 FepRequestDeserializer 등록:
        //    AbstractFepStruct.class 타입을 만날 때마다, **표준 변환 대신** FepRequestDeserializer를 사용하도록 지시합니다.
        //    이것이 동적 DTO 변환을 위한 **핵심 연결고리**입니다.
        //    - new FepRequestDeserializer(mapper, registry): Deserializer의 생성자에
        //      현재 ObjectMapper(재귀 방지용 cleanMapper 초기화)와 MessageTypeRegistry가 주입됩니다.
        module.addDeserializer(AbstractFepStruct.class, new FepRequestDeserializer(mapper, registry));

        // 4. 모듈 등록:
        //    생성한 커스텀 모듈을 ObjectMapper에 등록하여, 이제 이 ObjectMapper를 사용하는 모든 변환은
        //    AbstractFepStruct 타입을 만나면 FepRequestDeserializer를 호출하게 됩니다.
        mapper.registerModule(module);

        return mapper;
    }
}