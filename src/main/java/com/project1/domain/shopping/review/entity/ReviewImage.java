package com.project1.domain.shopping.review.entity;

import com.project1.global.generic.GenericImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter @Setter
@Entity @Table
@NoArgsConstructor
@SuperBuilder
public class ReviewImage extends GenericImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;
}
