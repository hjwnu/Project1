package com.project1.domain.shopping.payment.repository;

import com.project1.domain.shopping.payment.entity.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {
    PayInfo findPayInfoByOrder_OrderId(long orderNumber);
}
