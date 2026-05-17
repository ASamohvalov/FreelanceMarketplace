package com.srt.FreelanceMarketplace.service.domain.payment;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.payment.TransferRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferDomainService {
    private final TransferRepository repository;

    private final AccountDomainService accountDomainService;
    private final CommissionService commissionService;
    private final SystemFinanceDomainService systemFinanceDomainService;

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

        long commission = transfer.getAmount() - amount;
        systemFinanceDomainService.incrementEarnings(commission);
    }

    public TransferEntity getTransferByOrder(OrderEntity order) {
        return repository.findByOrder(order)
                .orElseThrow(() -> new IllegalStateException("transfer by the order not found"));
    }

    public List<TransferEntity> getWithServiceAndCustomerAllByAccount(AccountEntity account) {
        return repository.findWithServiceAndCustomerAllByRecipientAccountAndStatusOrderByCreatedAtDesc(
                account, TransferStatusEnum.RELEASED);
    }

    public List<TransferEntity> getWithServiceAndCustomerAllBySender(UserEntity sender) {
        return repository.findWithServiceAndCustomerAllByOrder_customerOrderByCreatedAtDesc(sender);
    }

    public void canselTransferByOrder(OrderEntity order) {
        TransferEntity transfer = getTransferByOrder(order);
        if (transfer.getStatus() != TransferStatusEnum.HELD) {
            throw new IllegalStateException("you cannot cancel an already released transfer");
        }
        transfer.setStatus(TransferStatusEnum.CANCELLED);
        repository.save(transfer);
    }
}
