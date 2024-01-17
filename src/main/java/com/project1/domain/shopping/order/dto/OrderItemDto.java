package com.project1.domain.shopping.order.dto;

import lombok.*;

import javax.annotation.Nullable;

@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItemDto {
    private Long itemId;
    @Nullable
    private String itemName;
    private Long count;
}
