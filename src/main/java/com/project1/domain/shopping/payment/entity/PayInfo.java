package com.project1.domain.shopping.payment.entity;

import com.project1.domain.shopping.order.entity.Order;
import lombok.*;

import javax.persistence.*;

@Entity@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String tid; // 결제 고유번호

}
