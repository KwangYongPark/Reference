package dev.binz.reference.fep.dto;

import lombok.*;

import java.util.Map;

/**
 * Fep 요청 전문 전체를 담는 최상위 DTO
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true) // AbstractData의 필드(comhead, body)를 비교 대상에 포함
public class FepRequestDto extends AbstractFepStruct<FepRequestDto.DataDto> {

    @EqualsAndHashCode(callSuper = true) // AbstractData의 필드(comhead, body)를 비교 대상에 포함
    public static class DataDto extends AbstractFepStruct.AbstractData<ComHeadDto, BodyDto> {
        // 필드는 AbstractData에 있으므로 비워둡니다.
    }
    /**
     * "data.comhead" 필드 내부의 구조를 담는 DTO
     */
    @Getter
    @Setter
    @ToString
    public static class ComHeadDto implements AbstractFepStruct.IComHead {
        private String code;
        private String log;

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
        private String message;
        private String key;
        private String ytpp;

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