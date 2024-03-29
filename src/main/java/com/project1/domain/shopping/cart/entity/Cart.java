package com.project1.domain.shopping.cart.entity;

import com.project1.global.generic.Auditable;
import com.project1.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@Slf4j
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public long getTotalPrice() {
        long price = 0;
        for (CartItem item : this.cartItems) {
            price += item.getItem().getPrice() * item.getCount();
        }
        return price;
    }
}
