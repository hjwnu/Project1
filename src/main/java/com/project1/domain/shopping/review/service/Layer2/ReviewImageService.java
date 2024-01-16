package com.project1.domain.shopping.review.service.Layer2;

import com.project1.domain.shopping.review.dto.ReviewImageResponseDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.entity.ReviewImage;
import com.project1.domain.shopping.review.mapper.ReviewMapper;
import com.project1.domain.shopping.review.repository.ReviewImageRepository;
import com.project1.global.generic.GenericImageService;
import com.project1.global.utils.S3;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service @Transactional
public class ReviewImageService
        extends GenericImageService.GenericImageServiceImpl<Review, ReviewImage, ReviewImageResponseDto>{
    private final ReviewImageRepository repository;
    private final ReviewMapper mapper;
    private final String PATH = "images/review/";

    public ReviewImageService(S3 s3, ReviewImageRepository repository, ReviewMapper mapper) {
        super(s3);
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    protected @NotNull String generateFileName(MultipartFile file, Review review) {
        return PATH +
                review.getItem().getName() +
                "_" +
                UUID.randomUUID().toString().substring(0, 10) +
                "_" +
                file.getOriginalFilename();
    }
    @Override
    protected JpaRepository<ReviewImage, Long> getRepository() {
        return repository;
    }
    @Override
    protected ReviewImageResponseDto imageToResponse(ReviewImage reviewImage) {
        return mapper.imageToResponse(reviewImage);
    }
    @Override
    protected List<ReviewImage> getImages(Review review) {
        return review.getImages();
    }
    @Override
    protected void setImages(Review entity, List<ReviewImage> images) {
        entity.setImages(images);
    }
    @Override
    protected ReviewImage imageBuild(Review review, MultipartFile file, String name) {
        return ReviewImage.builder().review((review))
                .originalName(file.getOriginalFilename())
                .imageName(name)
                .path(PATH)
                .build();
    }
    @Override
    protected Map<Long, List<ReviewImage>> fetch(List<Long> ids) {
        return repository.fetchReviewImages(ids);
    }

}
