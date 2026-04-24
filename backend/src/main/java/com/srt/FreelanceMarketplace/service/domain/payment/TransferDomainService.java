package com.srt.FreelanceMarketplace.service.domain.payment;

import com.srt.FreelanceMarketplace.domain.dto.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.repository.payment.TransferRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferDomainService {
    private final TransferRepository repository;

    private final AccountDomainService accountDomainService;
    private final CommissionService commissionService;

    /**
     * @param order required with freelancer.user and service
     */
    public void generateHeldTransfer(OrderEntity order) {
        AccountEntity account = accountDomainService.getByUser(order.getFreelancer().getUser());

        long price = commissionService.getFinalPrice(order.getService().getPrice());

        TransferEntity transfer = TransferEntity.builder()
                .order(order)
                .recipientAccount(account)
                .amount(price)
                .build();

        repository.save(transfer);
    }

    /**
     * @param transfer required account not empty
     */
    public void completeTransfer(TransferEntity transfer) {
        long amount = commissionService.getPriceWithoutCommission(transfer.getAmount());
        accountDomainService.add(transfer.getRecipientAccount(), amount);
        transfer.setStatus(TransferStatusEnum.RELEASED);
        repository.save(transfer);
    }

    public TransferEntity getTransferByOrder(OrderEntity order) {
        return repository.findByOrder(order)
                .orElseThrow(() -> new IllegalStateException("transfer by the order not found"));
    }
}
