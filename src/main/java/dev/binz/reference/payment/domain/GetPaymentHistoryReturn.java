package dev.binz.reference.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetPaymentHistoryReturn(
        String authTransactionId,
        BigDecimal authAmount,
        LocalDateTime authDate,
        String cancelTransactionId,
        BigDecimal cancelAmount,
        LocalDateTime cancelDate,
        boolean isCancelled,
        boolean hasCancelPermission
) {}