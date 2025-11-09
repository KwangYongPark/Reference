package dev.binz.reference.fep.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 변경된 JSON 구조를 담는 최상위 DTO (FepRequest2Dto)
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true) // AbstractData의 필드(comhead, body)를 비교 대상에 포함
public class FepRequest2Dto extends AbstractFepStruct<FepRequest2Dto.DataDto> {
    @EqualsAndHashCode(callSuper = true) // AbstractData의 필드(comhead, body)를 비교 대상에 포함
    public static class DataDto extends AbstractFepStruct.AbstractData<FepRequest2Dto.ComHeadDto, FepRequest2Dto.BodyDto> {
        // 필드는 AbstractData에 있으므로 비워둡니다.
    }

    /**
     * "data.comhead" 필드 내부의 구조를 담는 DTO
     */
    @Getter
    @Setter
    @ToString
    public static class ComHeadDto implements AbstractFepStruct.IComHead{
        // 필드명 변경: code -> code_id
        private String codeId;
        // 필드명 변경: log -> sout
        private String sout;

        @Override
        public String getCodeValue() {
            return "";
        }

        @Override
        public String getLogValue() {
            return "";
        }
    }

    /**
     * "data.body" 필드 내부의 구조를 담는 DTO
     */
    @Getter
    @Setter
    @ToString
    public static class BodyDto implements AbstractFepStruct.IBody{
        // 필드명 변경: message -> msg
        private String msg;

        // 필드명 유지: key
        private String key;

        // 필드명 변경: ytpp -> keyTyp
        private String keyTyp;

        @Override
        public String getMsgValue() {
            return "";
        }

        @Override
        public String getKeyValue() {
            return "";
        }

        @Override
        public String getKeyTypeValue() {
            return "";
        }
    }
}