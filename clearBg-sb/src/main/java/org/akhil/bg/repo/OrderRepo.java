package org.akhil.bg.repo;

import org.akhil.bg.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity,Long> {
    Optional<OrderEntity> findByOrderId(String orderId);
}
