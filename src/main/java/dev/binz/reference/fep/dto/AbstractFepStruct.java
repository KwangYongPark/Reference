package dev.binz.reference.fep.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.binz.reference.fep.FepRequestDeserializer;
import lombok.Getter;

import java.util.Map;


// IComHead, IBody

// =======================================================
// 추상 데이터 클래스: 데이터 구조를 강제 (head와 data)
// =======================================================

// T는 DataDto의 타입을 정의하는 Generic입니다.
@Getter
// 🚀 이 추상 클래스를 만날 때마다 커스텀 Deserializer를 사용하도록 지정
//@JsonDeserialize(using = FepRequestDeserializer.class)
public abstract class AbstractFepStruct<T extends AbstractFepStruct.AbstractData> {

    // =======================================================
    // 공통 인터페이스: 내부 데이터에 접근하는 공통 방법을 정의
    // =======================================================
    public interface IBody {
        String getMsgValue();   // message 또는 msg를 반환
        String getKeyValue();   // key를 반환 (필드명 동일)
        String getKeyTypeValue(); // ytpp 또는 keyTyp를 반환
    }

    /** "data.comhead"에 접근하는 공통 인터페이스 */
    public interface IComHead {
        String getCodeValue();  // code 또는 code_id를 반환
        String getLogValue();   // log 또는 sout를 반환
    }

    // 1. 두 요청 모두 동일한 Map<String, String> head 필드
    private Map<String, String> head;

    // 2. Data 구조는 동일하지만, 내부 타입이 다를 수 있으므로 T 타입으로 정의
    private T data;

    // Getter, Setter, ToString (Lombok 사용 시)
    public void of(Map<String, String> head, T data) { this.head = head; this.data = data; }

    // toString 구현 생략

    /**
     * "data" 필드 내부의 구조를 담는 추상 클래스
     * 내부 필드 ComHead와 Body의 타입을 제네릭으로 받습니다.
     */
    @Getter
    public abstract static class AbstractData<C extends IComHead, B extends IBody> {
        // 내부 DTO의 타입을 인터페이스로 강제
        private C comhead;
        private B body;

        public void setBody(C comhead, B body) { this.comhead = comhead; this.body = body; }
    }
}
