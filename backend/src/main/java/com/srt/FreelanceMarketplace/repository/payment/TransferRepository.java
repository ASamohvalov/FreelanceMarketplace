package com.srt.FreelanceMarketplace.repository.payment;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.TransferStatusEnum;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {
    Optional<TransferEntity> findByOrder(OrderEntity order);

    @EntityGraph(attributePaths = {"order.service", "order.customer"})
    List<TransferEntity> findWithServiceAndCustomerAllByRecipientAccountAndStatusOrderByCreatedAtDesc(
            AccountEntity recipientAccount, TransferStatusEnum status);

    @EntityGraph(attributePaths = {"order.service", "order.customer"})
    List<TransferEntity> findWithServiceAndCustomerAllByOrder_customerOrderByCreatedAtDesc(UserEntity customer);
}
