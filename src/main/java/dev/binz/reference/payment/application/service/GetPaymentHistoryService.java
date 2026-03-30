package dev.binz.reference.payment.application.service;

import dev.binz.reference.payment.adapter.out.persistence.PaymentHistoryJpaDto;
import dev.binz.reference.payment.adapter.out.persistence.PaymentRepository;
import dev.binz.reference.payment.application.port.in.GetPaymentHistoryCommand;
import dev.binz.reference.payment.application.port.in.GetPaymentHistoryUseCase;
import dev.binz.reference.payment.domain.GetPaymentHistoryReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPaymentHistoryService implements GetPaymentHistoryUseCase {

    private final PaymentRepository paymentRepository;

    @Override
    public Slice<GetPaymentHistoryReturn> getPaymentHistory(GetPaymentHistoryCommand command) {
        Slice<PaymentHistoryJpaDto> jpaDtoSlice = paymentRepository.findPaymentHistory(command.pageable());

        List<GetPaymentHistoryReturn> returnList = jpaDtoSlice.getContent().stream()
                .map(this::mapToGetPaymentHistoryReturn) // 수동 매핑
                .collect(Collectors.toList());

        return new SliceImpl<>(returnList, jpaDtoSlice.getPageable(), jpaDtoSlice.hasNext());
    }

    private GetPaymentHistoryReturn mapToGetPaymentHistoryReturn(PaymentHistoryJpaDto dto) {
        return new GetPaymentHistoryReturn(
                dto.getAuthTransactionId(), dto.getAuthAmount(), dto.getAuthDate(),
                dto.getCancelTransactionId(), dto.getCancelAmount(), dto.getCancelDate(),
                dto.isCancelled(), dto.isHasCancelPermission());
    }
}