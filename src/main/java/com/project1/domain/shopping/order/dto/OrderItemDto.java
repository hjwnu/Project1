package com.project1.domain.shopping.order.dto;

import lombok.*;

@Getter
@Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItemDto {
    private Long itemId;
    private String itemName;
    private Long count;
}
