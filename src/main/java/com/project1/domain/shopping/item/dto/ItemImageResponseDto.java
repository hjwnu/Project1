package com.project1.domain.shopping.item.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemImageResponseDto {
    Long itemImageId;
    Long itemId;
    String imageName;
    String URL;
    String representationImage;
}
