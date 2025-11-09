package dev.binz.reference.fep.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.binz.reference.fep.dto.AbstractFepStruct;
import dev.binz.reference.fep.dto.FepRequest3Dto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * FEP(Front-End Processor) 요청을 처리하는 메인 REST Controller입니다.
 * FEP의 동적 DTO 변환 로직 및 일반적인 Spring JSON 처리 방식을 비교하여 보여줍니다.
 */
@RestController
public class FepController {

    /**
     * 엔드포인트: /fep
     * 💡 요청 처리 방식: 커스텀 Argument Resolver(FepStructArgumentResolver)를 통한 동적 DTO 바인딩
     * * @RequestBody AbstractFepStruct<?>를 사용했기 때문에, Spring은 등록된 ArgumentResolver 목록을 순회합니다.
     * FepStructArgumentResolver가 이 인자를 처리하여, JSON의 "head.id" 값에 따라
     * FepRequestDto 또는 FepRequest2Dto와 같은 구체적인 DTO 인스턴스를 생성하여 주입합니다.
     *
     * @param requestBody 동적으로 구체화된 AbstractFepStruct 타입의 DTO
     */
    @PostMapping(path = "/fep")
    public Object incomming(@RequestBody AbstractFepStruct<?> requestBody) throws JsonProcessingException {
        System.out.println("FepController.incomming");

        // 🚀 DTO 출력 로직 (AOP로 분리되어야 하는 횡단 관심사)
        // 이전 단계에서 논의된 바와 같이, 이 로직은 로깅/디버깅 목적으로 Controller 외부에 AOP로 분리하는 것이 이상적입니다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonOutput = mapper.writeValueAsString(requestBody);
        System.out.println("Full DTO Content (JSON):\n" + jsonOutput);

        // 💡 주입된 DTO를 반환 (응답 본문으로 사용)
        return requestBody;
    }


    /**
     * 엔드포인트: /fep1
     * 💡 요청 처리 방식: 표준 Jackson ObjectMapper를 통한 Map 바인딩
     * * @RequestBody Map<String, ?>를 사용했기 때문에, Spring의 기본 처리기(RequestResponseBodyMethodProcessor)가
     * JSON 본문을 표준 Jackson ObjectMapper를 사용하여 Map<String, Object> 형태로 변환하여 주입합니다.
     * 이 방식은 동적 DTO 처리가 필요 없고, JSON 구조가 유연하거나 변경될 가능성이 있을 때 유용합니다.
     *
     * @param requestBody JSON 전체가 Map 형태로 변환된 객체
     */
    @PostMapping(path = "/fep1")
    public Object incomming2(@RequestBody Map<String, ?> requestBody) throws Exception {
        System.out.println("FepController.incomming");

        // ... (Map에 접근하여 로직 수행) ...
        System.out.println("requestBody = " + requestBody);

        // 💡 Map 객체를 반환
        return requestBody;
    }

    /**
     * 엔드포인트: /fep2
     * 💡 요청 처리 방식: 표준 Jackson ObjectMapper를 통한 정적 DTO 바인딩
     * * @RequestBody FepRequest3Dto를 사용했기 때문에, Spring의 기본 처리기가 JSON 본문을
     * 정적으로 FepRequest3Dto 클래스에 매핑하여 주입합니다.
     * 이 방식은 가장 일반적인 Spring DTO 처리 방식이며, JSON 필드명과 DTO 필드명이 정확히 일치해야 합니다.
     *
     * @param requestBody JSON이 정적으로 FepRequest3Dto 타입으로 변환된 객체
     */
    @PostMapping(path = "/fep2")
    public Object incomming3(@RequestBody FepRequest3Dto requestBody) throws Exception {
        System.out.println("FepController.incomming");

        // ... (DTO 필드에 접근하여 로직 수행) ...
        System.out.println("requestBody = " + requestBody);

        // 💡 정적 DTO 객체를 반환
        return requestBody;
    }
}