package com.mainproject.be28.domain.shopping.order.mapper;

import com.mainproject.be28.domain.shopping.order.dto.OrderDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.global.generic.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper
    extends GenericMapper<Order, OrderDto.PostDto, OrderDto.ResponseDto, OrderDto.PatchDto, Long> {

    default OrderDto.ResponseDto entityToResponseDto(Order order) {
        return OrderDto.ResponseDto.builder()
                .recipient(order.getDeliveryInfo().getRecipient())
                .address(order.getDeliveryInfo().getAddress())
                .phone(order.getDeliveryInfo().getPhoneNumber())
                .totalPrice(order.getTotalPrice())
                .orderItemList(order.getOrderItemList())
                .orderNumber(order.getOrderId())
                .status(order.getStatus())
                .build();
    }
}

