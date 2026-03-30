package dev.binz.reference.payment.adapter.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Querydsl을 사용하여 결제 내역을 조회하는 커스텀 Repository 인터페이스.
 */
public interface PaymentRepositoryCustom {
    Slice<PaymentHistoryJpaDto> findPaymentHistory(Pageable pageable);
}