package com.project1.domain.shopping.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {

    private Long itemId;
    private Long count;
}
