package com.project1.domain.shopping.item.entity;

import com.project1.global.generic.GenericImage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter @Setter
@Entity @Table
@NoArgsConstructor
@SuperBuilder
public class ItemImage extends GenericImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long itemImageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
