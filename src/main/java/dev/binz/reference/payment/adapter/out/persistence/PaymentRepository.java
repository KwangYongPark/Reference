package dev.binz.reference.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 결제 승인 내역에 대한 JPA Repository 인터페이스.
 * Querydsl을 사용한 커스텀 조회 기능을 위해 PaymentRepositoryCustom을 상속합니다.
 */
public interface PaymentRepository extends JpaRepository<PaymentAuthEntity, String>, PaymentRepositoryCustom {
}