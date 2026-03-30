package dev.binz.reference.payment.adapter.in.web;

import dev.binz.reference.payment.application.port.in.GetPaymentHistoryCommand;
import dev.binz.reference.payment.application.port.in.GetPaymentHistoryUseCase;
import dev.binz.reference.payment.domain.GetPaymentHistoryReturn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-history")
@RequiredArgsConstructor
public class GetPaymentHistoryController {

    private final GetPaymentHistoryUseCase getPaymentHistoryUseCase;

    @GetMapping
    public ResponseEntity<GetPaymentHistoryResponse> getPaymentHistory(
            @Valid @ModelAttribute GetPaymentHistoryRequest request
    ) {
        GetPaymentHistoryCommand command = request.toCommand();
        Slice<GetPaymentHistoryReturn> result = getPaymentHistoryUseCase.getPaymentHistory(command);
        GetPaymentHistoryResponse response = GetPaymentHistoryResponse.from(result);
        return ResponseEntity.ok(response);
    }
}