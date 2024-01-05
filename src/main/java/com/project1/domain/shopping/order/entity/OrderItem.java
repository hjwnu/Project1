package com.project1.domain.shopping.order.entity;

import com.project1.global.generic.Auditable;
import com.project1.domain.shopping.item.entity.Item;
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