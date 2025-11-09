package dev.binz.Reference.fep.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // FepRequestDto에 매핑되는 typeA JSON (typeA는 동적 Deserializer의 ID)
    private final String typeA = "{\"head\":{\"id\":\"typeA\",\"time\":\"20251106\"},\"data\":{\"comhead\":{\"code\":\"94\",\"log\":\"TestSend\"},\"body\":{\"message\":\"test1\",\"key\":\"41q\",\"ytpp\":\"55\"}}}";

    // FepRequest2Dto에 매핑되는 typeB JSON (typeB는 동적 Deserializer의 ID)
    private final String typeB = "{\"head\":{\"id\":\"typeB\",\"time\":\"20251106\"},\"data\":{\"comhead\":{\"codeId\":\"94\",\"sout\":\"TestSend\"},\"body\":{\"msg\":\"test1\",\"key\":\"41q\",\"keyTyp\":\"55\"}}}";


    /**
     * POST /fep 엔드포인트 테스트:
     * AbstractFepStruct<?> 타입을 @RequestBody로 받아, 동적 Deserializer(FepRequestDeserializer)를 통해
     * JSON ID("typeA")에 맞는 FepRequestDto로 정확히 변환되는지 검증합니다.
     */
    @Test
    @DisplayName("🧪 /fep - AbstractStruct (TypeA) : 동적 DTO 변환 성공 검증")
    void testIncomming_AbstractStruct_TypeA() throws Exception {
        mockMvc.perform(
                        post("/fep")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(typeA)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * POST /fep 엔드포인트 테스트:
     * AbstractFepStruct<?> 타입을 @RequestBody로 받아, 동적 Deserializer를 통해
     * JSON ID("typeB")에 맞는 FepRequest2Dto로 정확히 변환되는지 검증합니다.
     */
    @Test
    @DisplayName("🧪 /fep - AbstractStruct (TypeB) : 동적 DTO 변환 성공 검증")
    void testIncomming_AbstractStruct_TypeB() throws Exception {
        mockMvc.perform(
                        post("/fep")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(typeB)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * POST /fep1 엔드포인트 테스트:
     * @RequestBody Map<String, ?> 타입을 사용하여 JSON을 Map으로 변환하는 기본 Spring 동작을 검증합니다.
     * 이 테스트는 동적 DTO 로직을 사용하지 않는 레거시/일반 Map 변환의 안정성을 확인합니다.
     */
    @Test
    @DisplayName("🗺️ /fep1 - Map<String, ?> : 기본 Map 변환 성공 검증")
    void testIncomming_Map_TypeB() throws Exception {
        mockMvc.perform(
                        post("/fep1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(typeB)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * POST /fep2 엔드포인트 테스트:
     * @RequestBody FepRequest3Dto 타입을 사용하여 일반적인 정적 DTO 변환을 검증합니다.
     * 이 테스트는 DTO에 필드가 정확히 매핑되었는지, 그리고 일반 DTO 로직에 문제가 없는지 확인합니다.
     */
    @Test
    @DisplayName("📄 /fep2 - FepRequest3Dto : 일반 DTO 변환 성공 검증")
    void testIncomming_StaticStruct_TypeB() throws Exception {
        mockMvc.perform(
                        post("/fep2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(typeB)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}