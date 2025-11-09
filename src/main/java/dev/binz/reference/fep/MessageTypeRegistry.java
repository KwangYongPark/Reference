package dev.binz.reference.fep;

import dev.binz.reference.fep.dto.AbstractFepStruct;
import dev.binz.reference.fep.dto.FepRequest2Dto;
import dev.binz.reference.fep.dto.FepRequestDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * FEP 요청 JSON의 ID 값에 따라 처리할 구체적인 DTO 클래스를 매핑하여 저장하는 레지스트리입니다.
 * Jackson의 커스텀 Deserializer(FepRequestDeserializer)가 동적으로 클래스를 결정하는 데 사용됩니다.
 */
@Component // 💡 Spring Bean으로 등록하여 DI(의존성 주입)가 가능하도록 합니다.
public class MessageTypeRegistry {

    // 💡 JSON의 "head.id" (String) 값을 Key로, 매핑되는 구체 DTO 클래스(Class)를 Value로 저장합니다.
    private final Map<String, Class<? extends AbstractFepStruct<?>>> registry = new HashMap<>();

    /**
     * 애플리케이션 시작 시(Spring Context 초기화 후) 단 한 번 실행되어 모든 DTO 타입을 맵에 등록합니다.
     * 새로운 FEP 메시지 타입이 추가될 때마다 이 메서드에 등록해야 합니다.
     */
    @PostConstruct
    public void init() {
        // messageType: typeA - FepRequestDto 클래스에 매핑
        registry.put("typeA", FepRequestDto.class);

        // messageType: typeB - FepRequest2Dto 클래스에 매핑 (JSON 필드명이 다른 타입)
        // 참고: 주석에 있던 "9000"을 테스트 코드에 맞춰 "typeB"로 수정했습니다.
        registry.put("typeB", FepRequest2Dto.class);

        // 새로운 타입이 생길 때 여기에 추가만 하면 됩니다.
    }

    /**
     * 주어진 메시지 ID(JSON의 head.id)에 해당하는 구체 DTO 클래스를 반환합니다.
     *
     * @param messageType JSON 요청의 메시지 식별자 ("typeA", "typeB" 등)
     * @return 매핑된 DTO 클래스 (AbstractFepStruct를 상속받는 구체 클래스)
     */
    public Class<? extends AbstractFepStruct<?>> getType(String messageType) {
        return registry.get(messageType);
    }
}