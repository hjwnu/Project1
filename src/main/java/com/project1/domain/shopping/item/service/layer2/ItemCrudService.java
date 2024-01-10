package com.project1.domain.shopping.item.service.layer2;

import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.dto.ItemSearchCondition;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.mapper.ItemMapper;
import com.project1.domain.shopping.item.repository.ItemRepository;
import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.mapper.ReviewMapper;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCrudService
    extends GenericCrudService.GenericCrud<Item, ItemDto.Post, ItemDto.ResponseWithReview, ItemDto.Patch, Long> {
    private final ItemMapper mapper;
    private final ItemRepository repository;
    private final ReviewMapper reviewMapper;

    public ItemCrudService(ItemMapper mapper, ItemRepository repository, ReviewMapper reviewMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    protected JpaRepository<Item, Long> getRepository() {
        return repository;
    }

    @Override
    protected void setId(Item newEntity, Long aLong) {
        newEntity.setItemId(aLong);
    }

    @Override
    protected GenericMapper<Item, ItemDto.Post, ItemDto.ResponseWithReview, ItemDto.Patch, Long> getMapper() {
        return mapper;
    }

    @Override
    protected List<Item> findByName(String str) {
        return null;
    }

    public Item verifyExistItemName(String name) {
        return repository.findItemByName(name);
    }

    public List<ItemDto.ResponseDtoWithoutReview> conditionSearch(int page, List<ItemSearchCondition> itemSearchCondition) {

        List<Item> list = repository.searchByCondition(itemSearchCondition
                , PageRequest.of(page - 1, 9));
        return listToDtoList(list);
    }

    public ItemDto.ResponseWithReview entityToResponse(Item entity){
        ItemDto.ResponseDtoWithoutReview onlyitemResponseDtoWithoutReview = mapper.itemToItemResponseDto(entity, false);
        List<ReviewDto.ReviewResponseDto> reviewResponseList = getReviewsResponseDto(entity);
        return new ItemDto.ResponseWithReview(onlyitemResponseDtoWithoutReview, reviewResponseList);
    }
    public void removeStocks(Item item,Long count) {
        long stock = item.getStock() - count;
        if(stock< 0) throw new BusinessLogicException(ExceptionCode.LOW_STOCK);
        else item.setStock(stock);
    }

    private List<ReviewDto.ReviewResponseDto> getReviewsResponseDto(Item item) {
        List<ReviewDto.ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();
        List<Review> reviewList =  item.getReviews();
        if(reviewList != null) {
            for (Review review : reviewList) {
                ReviewDto.ReviewResponseDto reviewResponseDto =
                        reviewMapper.entityToResponseDto(review);
                reviewResponseDtoList.add(reviewResponseDto);
            }
        }
        return reviewResponseDtoList;
    }
    private List<ItemDto.ResponseDtoWithoutReview> listToDtoList(List<Item> itemList){
        return mapper.itemListToItemResponseDtoListWithoutReview(itemList, true);
    }
}
