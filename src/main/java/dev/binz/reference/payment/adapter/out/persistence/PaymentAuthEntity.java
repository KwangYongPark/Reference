package dev.binz.reference.payment.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAuthEntity {
    @Id private String transactionId;
    private BigDecimal amount;
    private LocalDateTime authDate;
}