package dev.binz.reference.fep;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import dev.binz.reference.fep.dto.AbstractFepStruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JSON의 "head.id" 값을 읽어 MessageTypeRegistry를 통해 실제 DTO 타입을 결정하고
 * 해당 타입으로 변환하는 동적 역직렬화(Polymorphic Deserialization)를 담당합니다.
 */
@Component
public class FepRequestDeserializer extends StdDeserializer<AbstractFepStruct<?>> {

    private final MessageTypeRegistry registry;
    private final ObjectMapper cleanMapper; // 💡 모든 Jackson 어노테이션을 무시하는 Mapper (무한 재귀 방지용)

    /**
     * FEP 전용 ObjectMapper와 메시지 레지스트리를 주입받아 초기화합니다.
     */
    public FepRequestDeserializer(ObjectMapper objectMapper, MessageTypeRegistry registry) {
        super(AbstractFepStruct.class);
        this.registry = registry;

        // 💡 1. cleanMapper를 생성자에서 단 한 번 초기화합니다. (성능 최적화)
        this.cleanMapper = objectMapper.copy();

        // 💡 2. @JsonDeserialize를 포함한 모든 Jackson 어노테이션을 무시하도록 설정합니다.
        //    이것이 무한 재귀 호출을 막는 핵심입니다.
        this.cleanMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
    }

    /**
     * JSON 파싱 흐름을 가로채어 동적으로 DTO를 변환합니다.
     */
    @Override
    @SneakyThrows // 💡 IOException 처리를 컴파일 시점에 자동으로 처리합니다.
    public AbstractFepStruct<?> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

        // 1. JSON 전체 노드를 JsonNode로 읽어 JSON 구조를 탐색합니다.
        JsonNode rootNode = jp.getCodec().readTree(jp);

        // 🚀 Null/유효성 체크: 필수 필드인 head와 id가 존재하는지 확인합니다.
        JsonNode headNode = rootNode.get("head");
        if (headNode == null || !headNode.isObject()) {
            // 🚨 수정: mappingException 대신 reportInputMismatch 사용
            throw (Throwable) ctxt.reportInputMismatch(
                    AbstractFepStruct.class, // 대상 클래스 명시
                    "JSON 요청에 필수 필드 'head'가 누락되었습니다."
            );
        }
        JsonNode messageIdNode = headNode.get("id");
        if (messageIdNode == null || !messageIdNode.isTextual()) {
            // 🚨 수정: mappingException 대신 reportInputMismatch 사용
            throw (Throwable) ctxt.reportInputMismatch(
                    AbstractFepStruct.class, // 대상 클래스 명시
                    "'head' 내에 필수 필드 'id'가 누락되었습니다."
            );
        }
        String messageId = messageIdNode.asText();

        // 2. 메시지 ID를 사용해 실제 변환할 구체 DTO 클래스를 레지스트리에서 조회합니다.
        Class<? extends AbstractFepStruct<?>> targetClass = registry.getType(messageId);
        if (targetClass == null) {
            // 🚨 수정: mappingException 대신 reportInputMismatch 사용
            throw (Throwable) ctxt.reportInputMismatch(
                    AbstractFepStruct.class, // 대상 클래스 명시
                    "등록되지 않은 messageId: %s",
                    messageId // 포맷 문자열 인자로 전달
            );
        }

        // 3. 현재 JsonNode를 TokenBuffer를 통해 새로운 파서로 변환합니다.
        //    이 과정은 현재의 파싱 위치와 상태를 그대로 복사하기 위해 필요합니다.
        ObjectMapper currentMapper = (ObjectMapper) jp.getCodec();
        TokenBuffer buffer = new TokenBuffer(currentMapper, false);
        buffer.writeTree(rootNode);
        JsonParser parserForTarget = buffer.asParser();
        parserForTarget.nextToken(); // START_OBJECT로 이동

        // 4. 🎯 무한 재귀 방지 Mapper(cleanMapper)를 사용해 최종 변환을 수행합니다.
        //    cleanMapper는 @JsonDeserialize를 무시하므로 재귀 호출 없이 targetClass로 안전하게 변환됩니다.
        return (AbstractFepStruct<?>) this.cleanMapper.readValue(parserForTarget, targetClass);
    }


    /**
     * @JsonDeserialize 등 모든 Jackson 어노테이션을 무시하도록 설정된 ObjectMapper를 생성합니다.
     * 이 Mapper는 무한 재귀를 유발하는 재귀 호출 고리를 끊는 데 사용됩니다.
     */
//    public static ObjectMapper createIgnoringAnnotationsMapper() {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        // 🚀 핵심 설정: MapperFeature.USE_ANNOTATIONS를 비활성화합니다.
//        mapper.configure(MapperFeature.USE_ANNOTATIONS, false);
//
//        // DTO에 정의된 @JsonIgnore 등의 다른 어노테이션도 함께 무시됩니다.
//
//        return mapper;
//    }
}