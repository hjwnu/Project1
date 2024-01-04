package com.mainproject.be28.domain.shopping.payment.entity;

import com.mainproject.be28.domain.shopping.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PayInfo {

    public PayInfo(String impUid) {
        this.impUid = impUid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String impUid;

    public void addOrder(Order order) {
        this.order = order;
//        if(order.getPayInfo() != this) {
//            order.addPayInfo(this);
//        }
    }
}
