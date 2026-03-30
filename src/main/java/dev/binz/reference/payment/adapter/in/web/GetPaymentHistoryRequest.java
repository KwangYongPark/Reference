package dev.binz.reference.payment.adapter.in.web;

import dev.binz.reference.payment.application.port.in.GetPaymentHistoryCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * '결제 내역 조회' 요청을 위한 DTO.
 * HTTP 요청 파라미터를 바인딩하고 Command 객체로 변환하는 역할을 합니다.
 */
@Getter
@Setter
public class GetPaymentHistoryRequest {
    @NotBlank private String userId;
    @Min(0) private int page = 0;
    @Min(1) private int size = 10;
    private String sortBy = "authDate";
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    public GetPaymentHistoryCommand toCommand() {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return new GetPaymentHistoryCommand(userId, pageable);
    }
}