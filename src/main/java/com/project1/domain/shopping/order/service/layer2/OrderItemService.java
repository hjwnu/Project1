package com.project1.domain.shopping.order.service.layer2;

import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.dto.OrderItemDto;
import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.domain.shopping.order.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderItemService {
    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }
    public List<OrderItem> orderItemDtoToEntityList(OrderDto.PostDto postDto, List<Item> items) {
        return postDto.getOrderItemList().stream()
                .map(dto -> {
                    Item item = items.stream()
                            .filter(i -> i.getItemId().equals(dto.getItemId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));
                    return new OrderItem(item, dto.getCount());
                })
                .collect(Collectors.toList());
    }
    public void saveAll(Order order) {
        for (OrderItem orderItem : order.getOrderItemList()) {
            orderItem.setOrder(order);
        }
        repository.saveAll(order.getOrderItemList());
    }

    public OrderItemDto entityToResponse(OrderItem orderItem) {
        return OrderItemDto.builder()
                .itemId(orderItem.getItem().getItemId())
                .itemName(orderItem.getItem().getName())
                .count(orderItem.getCount())
                .build();
    }

}
