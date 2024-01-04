package com.mainproject.be28.domain.shopping.order.entity;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.global.auditable.Auditable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
@RequiredArgsConstructor
public class OrderItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long count;

    public OrderItem(Item item, Long count) {
        this.item = item;
        this.count = count;
    }
}