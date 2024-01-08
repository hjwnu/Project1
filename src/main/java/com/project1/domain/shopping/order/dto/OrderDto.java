package com.project1.domain.shopping.order.dto;

import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class OrderDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class PostDto {
        @Nullable
        private String recipient;
        @Nullable
        private String phone;
        @Nullable
        private String address;
        private boolean sameMemberAndRecipient;
        private List<OrderItemDto> orderItemList;
        // private PayInfo payinfo; // 결제 구현 시 활용

    }

    @Getter  @Setter
    @NoArgsConstructor
    public static class PatchDto {
        @Nullable
        private String recipient;
        @Nullable
        private String phone;
        @Nullable
        private String address;
        @Nullable
        private Order.Status status;
        @Nullable
        private Map<Long, Long> itemAndQuantity;
    }
    @Getter  @Setter
    @Builder
    public static class ResponseDto {
        private Long orderNumber;
        private String recipient;
        private String phone;
        private String address;
        private List<OrderItemDto> orderItemList;
        private Long totalPrice;
        private Order.Status status;
    }


}
