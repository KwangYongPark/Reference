package dev.binz.reference.payment.adapter.in.web;

import dev.binz.reference.payment.domain.GetPaymentHistoryReturn;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * '결제 내역 조회' 응답을 위한 DTO.
 * 유스케이스의 반환 객체를 HTTP 응답 형식으로 변환하는 역할을 합니다.
 */
@Getter
@Builder
public class GetPaymentHistoryResponse {
    private List<PaymentHistoryItem> historyItems;
    private boolean hasNext;
    private int pageNumber;
    private int pageSize;

    public static GetPaymentHistoryResponse from(Slice<GetPaymentHistoryReturn> result) {
        List<PaymentHistoryItem> items = result.getContent().stream()
                .map(PaymentHistoryItem::from)
                .collect(Collectors.toList());

        return GetPaymentHistoryResponse.builder()
                .historyItems(items)
                .hasNext(result.hasNext())
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .build();
    }

    @Getter
    @Builder
    public static class PaymentHistoryItem {
        private String authTransactionId;
        private BigDecimal authAmount;
        private LocalDateTime authDate;
        private String cancelTransactionId;
        private BigDecimal cancelAmount;
        private LocalDateTime cancelDate;
        private boolean isCancelled;
        private boolean hasCancelPermission;

        public static PaymentHistoryItem from(GetPaymentHistoryReturn item) {
            return PaymentHistoryItem.builder()
                    .authTransactionId(item.authTransactionId())
                    .authAmount(item.authAmount())
                    .authDate(item.authDate())
                    .cancelTransactionId(item.cancelTransactionId())
                    .cancelAmount(item.cancelAmount())
                    .cancelDate(item.cancelDate())
                    .isCancelled(item.isCancelled())
                    .hasCancelPermission(item.hasCancelPermission())
                    .build();
        }
    }
}