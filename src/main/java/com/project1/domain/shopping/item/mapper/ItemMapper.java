package com.project1.domain.shopping.item.mapper;

import com.project1.global.generic.GenericMapper;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.dto.ItemImageDto;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.ItemImage;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel =  "spring")
public interface ItemMapper
        extends GenericMapper<Item, ItemDto.Post, ItemDto.ResponseWithReview, ItemDto.Patch> {
    default ItemDto.ResponseDtoWithoutReview itemToItemResponseDto(Item item, boolean isList) {
        if ( item == null ) {
            return null;
        }

        return ItemDto.ResponseDtoWithoutReview.builder()
                    .itemId( item.getItemId() )
                    .name( item.getName() )
                    .price( item.getPrice() )
                    .detail( item.getDetail() )
                    .color( item.getColor() )
                    .brand( item.getBrand() )
                    .stocks( checkStock(item) )
                    .category( item.getCategory() )
                    .score(  isList ? item.getScore() : item.getCustomScore() )
                    .reviewCount( isList ? item.getReviewCount() : item.getCustomReviewCount() )
                    .imageURLs( getImageResponseDto(item) )
                    .build();
    }


    default List<ItemDto.ResponseDtoWithoutReview> itemListToItemResponseDtoListWithoutReview(List<Item> itemList, boolean isList) {
        return itemList.stream().map(item -> itemToItemResponseDto(item,isList)).collect(Collectors.toList());
    }

    default List<ItemImageDto> getImageResponseDto(Item item) {
        List<ItemImageDto> itemImageDtoList = new ArrayList<>();
        List<ItemImage> imageList = item.getImages();

        if (imageList != null) {
            for (ItemImage image : imageList) {
                ItemImageDto itemImageDto = imageToResponse(image);
                itemImageDtoList.add(itemImageDto);
            }
        }
        return itemImageDtoList;
    }

    default ItemImageDto imageToResponse(ItemImage image) {
        return ItemImageDto.builder()
                .imageName(image.getImageName())
                .URL(image.getBaseUrl() + image.getImageName())
                .representationImage(image.getRepresentationImage())
                .build();
    }


    default String checkStock(Item item) {
        if(item.getStock()>10) {return "재고 있음";}
        else if(item.getStock()>0){return String.format("%d개 남음", item.getStock());}
        else return "품절";
    }

}