package com.project1.domain.shopping.order.entity;

import com.project1.domain.shopping.payment.entity.PayInfo;
import com.project1.global.generic.Auditable;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.cart.entity.Cart;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Builder
@Table (name = "orders")
public class Order extends Auditable {

    @Id
    private Long orderId;     // 주문 번호

    private Status status = Status.ORDER_PLACED; // 주문 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryInfo deliveryInfo;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PayInfo payInfo;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList;

    public void addItem(OrderItem item) {
        this.orderItemList.add(item);
    }
    public long getTotalPrice() {
        long sum = 0;
        for (OrderItem item : this.orderItemList) {
           sum += item.getCount()*item.getItem().getPrice();
        }
        return sum;
    }
    public enum Status {
        ORDER_PLACED(0, "주문접수"),
        PAYMENT_WAIT(1, "결제대기"),
        PAYMENT_COMPLETED(2, "결제완료"),
        DELIVERY_PREPARING(3, "배송준비"),
        DELIVERY_IN_PROGRESS(4, "배송중"),
        DELIVERY_COMPLETED(5, "배송완료"),
        ORDER_CANCELED(6, "주문취소"),
        REFUND_APPLIED(7, "환불처리중"),
        REFUNDED(8, "환불완료"),
        PAYMENT_AMOUNT_WRONG(9, "결제오류");

        @Getter
        private final int index;
        @Getter
        private final String status;

        Status(int index, String status) {
            this.index = index;
            this.status = status;
        }
    }

    @PrePersist
    public void onPrePersist() {
        if (this.orderId == null) {
            String date = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            this.orderId =Long.parseLong(date + RandomStringUtils.randomNumeric(4));
        }
    }
}
