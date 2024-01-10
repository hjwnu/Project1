package com.project1.domain.shopping.item.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemImageDto {
    String imageName;
    String URL;
    String representationImage;
}
