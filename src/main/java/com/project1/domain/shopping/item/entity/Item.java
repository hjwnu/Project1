package com.project1.domain.shopping.item.entity;

import com.project1.domain.shopping.review.entity.Review;
import com.project1.global.generic.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String detail;

    @Column
    private Long price;

    @Column(length = 100)
    private Long stock;

    @Column(length = 100)
    private String color;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String category;

    @Transient
    private Double score;

    @Transient
    private Integer reviewCount;

    @OneToMany(mappedBy = "item",  cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ItemImage> Images;

    public void addImage(ItemImage i) {
        this.Images.add(i);
    }

    public Double getCustomScore(){
        if (this.reviews == null) {
            this.score = this.score==null ? 0.0:this.score;
            return this.score;
        }

        this.score = this.score==null ? 0.0:this.score;
        List<Review> itemList =  this.getReviews();
        double score = 0D;
        for (Review review : itemList) {
            score += review.getScore();
        }
        score /= itemList.size();
        score = (double)Math.round(score*100)/100;
        return score;
    }
//
    public Integer getCustomReviewCount() {
        if (this.reviews == null) return 0;
        return this.reviews.size();
    }

    public void removeStocks(long count) {
        this.stock = this.stock-count;
    }
}
