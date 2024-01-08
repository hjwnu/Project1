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
import java.util.stream.Collectors;
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
    protected JpaRepository<ReviewImage, Long> getRepository() {
        return repository;
    }

    @Override
    protected Map<Long, List<ReviewImageResponseDto>> fetchImages(List<Long> ids) {
        Map<Long, List<ReviewImage>> imageMap = fetch(ids);
        return imageMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<Long,List<ReviewImage>>::getKey,
                        entry -> entry.getValue().stream()
                                .map(mapper::imageToResponse)
                                .collect(Collectors.toList())
                ));
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
    protected @NotNull String generateFileName(MultipartFile file, Review review) {
        return new StringBuilder()
                .append(PATH)
                .append(review.getItem().getName().toUpperCase())
                .append("_")
                .append(UUID.randomUUID().toString(), 0, 10)
                .append("_")
                .append(file.getOriginalFilename())
                .toString();
    }

    @Override
    protected Map<Long, List<ReviewImage>> fetch(List<Long> ids) {
        return repository.fetchReviewImages(ids);
    }

}
