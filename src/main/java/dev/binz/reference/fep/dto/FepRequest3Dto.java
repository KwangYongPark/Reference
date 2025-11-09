package dev.binz.reference.fep.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 변경된 JSON 구조를 담는 최상위 DTO (FepRequest2Dto)
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FepRequest3Dto {

    private Map<String, String> head;
    private DataDto data;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class DataDto {
        private ComHeadDto comhead;
        private BodyDto body;
    }

    /**
     * "data.comhead" 필드 내부의 구조를 담는 DTO
     */
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ComHeadDto{
        // 필드명 변경: code -> code_id
        private String codeId;
        // 필드명 변경: log -> sout
        private String sout;
    }

    /**
     * "data.body" 필드 내부의 구조를 담는 DTO
     */
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class BodyDto{
        // 필드명 변경: message -> msg
        private String msg;

        // 필드명 유지: key
        private String key;

        // 필드명 변경: ytpp -> keyTyp
        private String keyTyp;
    }
}