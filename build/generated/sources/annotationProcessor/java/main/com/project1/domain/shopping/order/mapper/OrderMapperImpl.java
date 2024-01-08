package com.project1.domain.shopping.order.mapper;

import com.project1.domain.shopping.order.dto.OrderDto.PatchDto;
import com.project1.domain.shopping.order.dto.OrderDto.PostDto;
import com.project1.domain.shopping.order.dto.OrderItemDto;
import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.Order.OrderBuilder;
import com.project1.domain.shopping.order.entity.OrderItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-08T13:04:14+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order postDtoToEntity(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        order.orderItemList( orderItemDtoListToOrderItemList( postDto.getOrderItemList() ) );

        return order.build();
    }

    @Override
    public Order patchDtoToEntity(PatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        return order.build();
    }

    protected OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        if ( orderItemDto == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setCount( orderItemDto.getCount() );

        return orderItem;
    }

    protected List<OrderItem> orderItemDtoListToOrderItemList(List<OrderItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItem> list1 = new ArrayList<OrderItem>( list.size() );
        for ( OrderItemDto orderItemDto : list ) {
            list1.add( orderItemDtoToOrderItem( orderItemDto ) );
        }

        return list1;
    }
}
