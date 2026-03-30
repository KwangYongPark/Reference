package dev.binz.reference.payment.application.port.in;

import dev.binz.reference.payment.domain.GetPaymentHistoryReturn;
import org.springframework.data.domain.Slice;

/**
 * '결제 내역 조회' 유스케이스의 인바운드 포트 인터페이스.
 * 애플리케이션 서비스가 구현해야 할 계약을 정의합니다.
 */
public interface GetPaymentHistoryUseCase {
    Slice<GetPaymentHistoryReturn> getPaymentHistory(GetPaymentHistoryCommand command);
}