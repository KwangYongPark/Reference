package dev.binz.reference.payment.application.port.in;

import org.springframework.data.domain.Pageable;

/**
 * '결제 내역 조회' 유스케이스의 입력 Command.
 * Controller에서 받은 요청 데이터를 서비스 계층으로 전달하는 역할을 합니다.
 */
public record GetPaymentHistoryCommand(
        String userId, Pageable pageable
) {}