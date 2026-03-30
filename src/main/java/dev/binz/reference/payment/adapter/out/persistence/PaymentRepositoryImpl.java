package dev.binz.reference.payment.adapter.out.persistence;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dev.binz.reference.payment.adapter.out.persistence.QPaymentAuthEntity.paymentAuthEntity;
import static dev.binz.reference.payment.adapter.out.persistence.QPaymentCancelEntity.paymentCancelEntity;

@Repository
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Slice<PaymentHistoryJpaDto> findPaymentHistory(Pageable pageable) {
        List<PaymentHistoryJpaDto> content = queryFactory
                .select(new QPaymentHistoryJpaDto( // @QueryProjection이 적용된 생성자 사용
                        paymentAuthEntity.transactionId,
                        paymentAuthEntity.amount,
                        paymentAuthEntity.authDate,
                        paymentCancelEntity.transactionId,
                        paymentCancelEntity.amount,
                        paymentCancelEntity.cancelDate,
                        paymentCancelEntity.transactionId.isNotNull(), // isCancelled 필드
                        Expressions.asBoolean(true) // hasCancelPermission 고정값 주입
                ))
                .from(paymentAuthEntity)
                .leftJoin(paymentCancelEntity).on(paymentAuthEntity.transactionId.eq(paymentCancelEntity.transactionId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 다음 페이지 존재 여부 확인을 위해 하나 더 가져옴
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) content.remove(pageable.getPageSize());

        return new SliceImpl<>(content, pageable, hasNext);
    }
}