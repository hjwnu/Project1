package com.mainproject.be28.domain.shopping.item.entity;

import com.mainproject.be28.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity @Table
@Builder
public class ItemImage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long itemImageId;

    @Column(length = 100)
    private String imageName;

    @Column
    private String originalName;

    @Column(length = 100)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column
    private String representationImage; // "Yes" , " No" 문자열로 구분

    @Transient
    final String baseUrl = "http://be28.s3-website.ap-northeast-2.amazonaws.com";

    public void updateItemImage(String originalName, String imageName, String path) {
        this.originalName = originalName;
        this.imageName = imageName;
        this.path = path;
    }
}
