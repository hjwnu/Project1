package com.project1.domain.shopping.order.service.layer2;

import com.project1.domain.member.auth.jwt.JwtTokenizer;
import com.project1.domain.shopping.cart.entity.Cart;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderCrudService
    extends GenericCrudService.GenericCrud<Order, OrderDto.PostDto, OrderDto.ResponseDto, OrderDto.PatchDto, Long> {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public OrderCrudService(OrderRepository repository, OrderMapper mapper, JwtTokenizer jwtTokenizer) {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    public Order create(OrderDto.PostDto postDto, Cart cart, Member member, DeliveryInfo info, List<Item> item) {
        Order order = postDtoToEntity(postDto, cart, member, info, item);
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

    public Page<OrderDto.ResponseDto> findByName(Member member, int page, int size) {
        List<OrderDto.ResponseDto> dtoList = repository.findOrderByMember_Name(member.getName())
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, PageRequest.of(page-1,size),dtoList.size());
    }
    @Override
    public OrderDto.ResponseDto entityToResponse(Order order) {
        OrderDto.ResponseDto responseDto = mapper.entityToResponseDto(order);
        decodePrivacy(responseDto);
        return responseDto;
    }
    private Order postDtoToEntity(OrderDto.PostDto postDto, Cart cart, Member member, DeliveryInfo info, List<Item> item) {
        List<OrderItem> list = orderItemDtoToEntityList(postDto, item);
        return Order.builder()
                .status(Order.Status.ORDER_PLACED)
                .orderItemList(list)
                .member(member)
                .cart(cart)
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
    private void decodePrivacy(OrderDto.ResponseDto responseDto) {
        responseDto.setAddress(jwtTokenizer.dataEnDecrypt(responseDto.getAddress(), 2));
        responseDto.setPhone(jwtTokenizer.dataEnDecrypt(responseDto.getPhone(), 2));
    }
}