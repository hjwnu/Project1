package com.mainproject.be28.domain.shopping.item.mapper;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemImageResponseDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import com.mainproject.be28.global.generic.GenericMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
@Mapper(componentModel =  "spring")
public interface ItemMapper
        extends GenericMapper<Item, ItemDto.Post, ItemDto.Response, ItemDto.Patch, Long>  {
    default OnlyItemResponseDto itemToOnlyItemResponseDto(Item item) {
        if ( item == null ) {
            return null;
        }

        OnlyItemResponseDto.OnlyItemResponseDtoBuilder onlyItemResponseDto =
                OnlyItemResponseDto.builder()
                    .itemId( item.getItemId() )
                    .name( item.getName() )
                    .price( item.getPrice() )
                    .detail( item.getDetail() )
                    .color( item.getColor() )
                    .score( item.getScore() )
                    .brand( item.getBrand() )
                    .stocks(checkStock(item))
                    .category( item.getCategory() )
                    .reviewCount(item.getReviewCount())
                    .imageURLs(getImageResponseDto(item));

        return onlyItemResponseDto.build();
    }

    default List<OnlyItemResponseDto> itemListToOnlyItemResponseDtoList(List<Item> itemList) {
        List<OnlyItemResponseDto> onlyItemResponsesDto = new ArrayList<>();
        for (Item item : itemList) {
            onlyItemResponsesDto.add(itemToOnlyItemResponseDto(item));
        }
        return onlyItemResponsesDto;
    }

    default List<ItemImageResponseDto> getImageResponseDto(Item item) {
        List<ItemImageResponseDto> itemImageResponseDtos = new ArrayList<>();
        List<ItemImage> imageList = item.getImages();

        if (imageList != null) {
            for (ItemImage image : imageList) {
                ItemImageResponseDto itemImageResponseDto = imageToResponse(image);
                itemImageResponseDtos.add(itemImageResponseDto);
            }
        }
        return itemImageResponseDtos;
    }

    default ItemImageResponseDto imageToResponse(ItemImage image) {
        return ItemImageResponseDto.builder()
                . itemId(image.getItem().getItemId())
                .itemImageId(image.getItemImageId())
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