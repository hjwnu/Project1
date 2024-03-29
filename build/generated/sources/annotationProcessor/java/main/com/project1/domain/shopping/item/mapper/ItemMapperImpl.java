package com.project1.domain.shopping.item.mapper;

import com.project1.domain.shopping.item.dto.ItemDto.Patch;
import com.project1.domain.shopping.item.dto.ItemDto.Post;
import com.project1.domain.shopping.item.dto.ItemDto.ResponseDtoWithoutReview;
import com.project1.domain.shopping.item.dto.ItemDto.ResponseWithReview;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.Item.ItemBuilder;
import com.project1.domain.shopping.review.dto.ReviewDto.ReviewResponseDto;
import com.project1.domain.shopping.review.dto.ReviewDto.ReviewResponseDto.ReviewResponseDtoBuilder;
import com.project1.domain.shopping.review.entity.Review;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T09:56:46+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item postDtoToEntity(Post postDto) {
        if ( postDto == null ) {
            return null;
        }

        ItemBuilder item = Item.builder();

        item.itemId( postDto.getItemId() );
        item.name( postDto.getName() );
        item.detail( postDto.getDetail() );
        item.price( postDto.getPrice() );
        item.stock( postDto.getStock() );
        item.color( postDto.getColor() );
        item.brand( postDto.getBrand() );
        item.category( postDto.getCategory() );

        return item.build();
    }

    @Override
    public Item patchDtoToEntity(Patch patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        ItemBuilder item = Item.builder();

        item.itemId( patchDto.getItemId() );
        item.name( patchDto.getName() );
        item.detail( patchDto.getDetail() );
        item.price( patchDto.getPrice() );
        item.stock( patchDto.getStock() );
        item.color( patchDto.getColor() );
        item.brand( patchDto.getBrand() );
        item.category( patchDto.getCategory() );

        return item.build();
    }

    @Override
    public ResponseWithReview entityToResponseDto(Item entity) {
        if ( entity == null ) {
            return null;
        }

        List<ReviewResponseDto> reviews = null;

        reviews = reviewListToReviewResponseDtoList( entity.getReviews() );

        ResponseDtoWithoutReview item = null;

        ResponseWithReview responseWithReview = new ResponseWithReview( item, reviews );

        return responseWithReview;
    }

    protected ReviewResponseDto reviewToReviewResponseDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponseDtoBuilder reviewResponseDto = ReviewResponseDto.builder();

        reviewResponseDto.content( review.getContent() );
        reviewResponseDto.score( review.getScore() );
        reviewResponseDto.modifiedAt( review.getModifiedAt() );
        reviewResponseDto.createdAt( review.getCreatedAt() );
        reviewResponseDto.likeCount( review.getLikeCount() );
        reviewResponseDto.unlikeCount( review.getUnlikeCount() );

        return reviewResponseDto.build();
    }

    protected List<ReviewResponseDto> reviewListToReviewResponseDtoList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewResponseDto> list1 = new ArrayList<ReviewResponseDto>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewToReviewResponseDto( review ) );
        }

        return list1;
    }
}
