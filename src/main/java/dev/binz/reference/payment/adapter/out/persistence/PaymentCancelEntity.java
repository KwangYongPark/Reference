package dev.binz.reference.payment.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_cancel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelEntity {
    @Id private String transactionId;
    private BigDecimal amount;
    private LocalDateTime cancelDate;
}