package com.project1.domain.shopping.review.repository;

import com.project1.domain.shopping.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, CustomReviewImageRepository {
}
