package com.project1.domain.shopping.order.service.layer2;

import com.project1.domain.shopping.order.repository.OrderRepository;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.entity.DeliveryInfo;
import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.domain.shopping.order.mapper.OrderMapper;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCrudService
    extends GenericCrudService.GenericCrud<Order, OrderDto.PostDto, OrderDto.ResponseDto, OrderDto.PatchDto, Long> {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderCrudService(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Order create(OrderDto.PostDto postDto, Member member, List<Item> item) {
        Order order = postDtoToEntity(postDto, member,item);
        return getRepository().save(order);
    }
    public Order find(long orderNumber) {
        return repository.findById(orderNumber).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return repository;
    }
    @Override
    protected void setId(Order newEntity, Long aLong) {
        newEntity.setOrderId(aLong);
    }
    @Override
    protected GenericMapper<Order, OrderDto.PostDto, OrderDto.ResponseDto, OrderDto.PatchDto, Long> getMapper() {
        return mapper;
    }
    @Override
    protected List<Order> findByName(String str) {
        return repository.findOrderByMember_Name(str);
    }

    private Order postDtoToEntity(OrderDto.PostDto postDto, Member member, List<Item> item) {
        List<OrderItem> list = orderItemDtoToEntityList(postDto, item);
        DeliveryInfo info;
        if (postDto.isSameMemberAndRecipient()) {
            info = createDeliveryInfo(member.getName(),member.getPhone(), member.getAddress());
        } else info = createDeliveryInfo(postDto.getRecipient(), postDto.getPhone(), postDto.getAddress());

        return  Order.builder()
                .status(Order.Status.ORDER_PLACED)
                .orderItemList(list)
                .deliveryInfo(info)
                .build();
    }
    private List<OrderItem> orderItemDtoToEntityList(OrderDto.PostDto postDto, List<Item> items) {
        return postDto.getOrderItemList().stream()
                .map(dto -> new OrderItem(
                        items.stream()
                                .filter(item -> item.getItemId().equals(dto.getItemId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID")),
                        dto.getCount()))
                .collect(Collectors.toList());
    }
    private DeliveryInfo createDeliveryInfo(String recipient, String phone, String address) {
        return new DeliveryInfo().createDeliverInfo(recipient, phone,address);
    }

}
