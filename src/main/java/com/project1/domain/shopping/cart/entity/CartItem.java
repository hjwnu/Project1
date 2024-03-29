package com.project1.domain.shopping.cart.entity;

import com.project1.domain.shopping.item.entity.Item;
import com.project1.global.generic.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class CartItem extends Auditable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column()
        private Long cartItemId;

        @Column(nullable = false)
        private Long count;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "CART_ID", nullable = false)
        private Cart cart;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ITEM_ID", nullable = false)
        private Item item;

        public void addCount(long count) {
                this.count += count;
        }
}
