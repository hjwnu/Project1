package com.project1.domain.shopping.order.mapper;

import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.dto.OrderItemDto;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.shopping.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper
    extends GenericMapper<Order, OrderDto.PostDto, OrderDto.ResponseDto, OrderDto.PatchDto, Long> {

    default OrderDto.ResponseDto entityToResponseDto(Order order) {
        return OrderDto.ResponseDto.builder()
                .recipient(order.getDeliveryInfo().getRecipient())
                .address(order.getDeliveryInfo().getAddress())
                .phone(order.getDeliveryInfo().getPhone())
                .totalPrice(order.getTotalPrice())
                .orderItemList(orderItemToResponse(order.getOrderItemList()))
                .orderNumber(order.getOrderId())
                .status(order.getStatus())
                .build();
    }

    default List<OrderItemDto> orderItemToResponse(List<OrderItem> orderItemList) {
        List<OrderItemDto> dtoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setItemId(orderItem.getItem().getItemId());
            orderItemDto.setItemName(orderItem.getItem().getName());
            orderItemDto.setCount(orderItem.getCount());

            dtoList.add(orderItemDto);
        }
        return dtoList;
    }
}

