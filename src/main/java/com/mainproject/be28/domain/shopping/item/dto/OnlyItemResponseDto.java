package com.mainproject.be28.domain.shopping.item.dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OnlyItemResponseDto {
    private Long itemId;
    private String name;
    private Long price;
    private String detail;
    private String stocks;
    private String color;
    private Double score;
    private String brand;
    private String category;
    private Integer reviewCount;
    private List<ItemImageResponseDto> imageURLs;
}
