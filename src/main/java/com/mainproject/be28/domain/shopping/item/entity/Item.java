package com.mainproject.be28.domain.shopping.item.entity;

import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Item extends Auditable{
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

    public void removeStocks(Long count) {
        long stock = this.stock - count;
        if(stock< 0) throw new BusinessLogicException(ExceptionCode.LOW_STOCK);
        else this.stock = stock;
    }

    public void setRepresentationImage() {
        ItemImage repImg = this.Images.get(0);
        if(repImg.getRepresentationImage()==null||!repImg.getRepresentationImage().equals("YES")) {
            repImg.setRepresentationImage("YES");
            this.Images.set(0, repImg);
        }
    }
    public Double getScore(){
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

    public Integer getReviewCount() {
        if (this.reviews == null) return 0;
        return this.reviews.size();
    }
}
