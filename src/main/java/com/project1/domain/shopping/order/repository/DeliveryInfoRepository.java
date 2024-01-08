package com.project1.domain.shopping.order.repository;

import com.project1.domain.shopping.order.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
}
