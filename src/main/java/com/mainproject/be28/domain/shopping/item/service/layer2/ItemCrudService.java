package com.mainproject.be28.domain.shopping.item.service.layer2;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchCondition;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.mapper.ItemMapper;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.mapper.ReviewMapper;
import com.mainproject.be28.global.generic.GenericCrudService;
import com.mainproject.be28.global.generic.GenericMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCrudService
    extends GenericCrudService.GenericCrud<Item, ItemDto.Post, ItemDto.Response, ItemDto.Patch, Long>{
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
    protected GenericMapper<Item, ItemDto.Post, ItemDto.Response, ItemDto.Patch, Long> getMapper() {
        return mapper;
    }

    @Override
    protected List<Item> findByName(String str) {
        return null;
    }

    public Item verifyExistItemName(String name) {
        return repository.findItemByName(name);
    }

    public List<OnlyItemResponseDto> conditionSearch(int page, List<ItemSearchCondition> itemSearchCondition) {

        List<Item> list = repository.searchByCondition(itemSearchCondition
                , PageRequest.of(page - 1, 9));

        return listToDtoList(list);
    }

    public ItemDto.Response entityToResponse(Item entity){
        OnlyItemResponseDto onlyitemResponseDto = mapper.itemToOnlyItemResponseDto(entity);
        List<ReviewResponseDto> reviewResponseList = getReviewsResponseDto(entity);
        return new ItemDto.Response(onlyitemResponseDto, reviewResponseList);
    }

    private List<ReviewResponseDto> getReviewsResponseDto(Item item) {
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();
        List<Review> reviewList =  item.getReviews();
        if(reviewList != null) {
            for (Review review : reviewList) {
                ReviewResponseDto reviewResponseDto =
                        reviewMapper.entityToResponseDto(review);
                reviewResponseDtoList.add(reviewResponseDto);
            }
        }
        return reviewResponseDtoList;
    }

    private List<OnlyItemResponseDto> listToDtoList(List<Item> itemList){
        return mapper.itemListToOnlyItemResponseDtoList(itemList);
    }
}
