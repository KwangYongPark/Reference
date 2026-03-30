package dev.binz.reference.payment.adapter.out.persistence;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // Querydsl이 기본 생성자를 필요로 할 수 있습니다.
public class PaymentHistoryJpaDto {
    private String authTransactionId;
    private BigDecimal authAmount;
    private LocalDateTime authDate;
    private String cancelTransactionId;
    private BigDecimal cancelAmount;
    private LocalDateTime cancelDate;
    private boolean isCancelled;
    private boolean hasCancelPermission;

    @QueryProjection // 표준 생성자에 @QueryProjection 적용
    public PaymentHistoryJpaDto(
            String authTransactionId, BigDecimal authAmount, LocalDateTime authDate,
            String cancelTransactionId, BigDecimal cancelAmount, LocalDateTime cancelDate,
            boolean isCancelled, boolean hasCancelPermission
    ) {
        this.authTransactionId = authTransactionId;
        this.authAmount = authAmount;
        this.authDate = authDate;
        this.cancelTransactionId = cancelTransactionId;
        this.cancelAmount = cancelAmount;
        this.cancelDate = cancelDate;
        this.isCancelled = isCancelled;
        this.hasCancelPermission = hasCancelPermission;
    }
}