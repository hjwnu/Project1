package com.project1.domain.shopping.order.service.layer1;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.entity.DeliveryInfo;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.domain.shopping.order.service.layer2.DeliveryInfoService;
import com.project1.domain.shopping.order.service.layer2.OrderCrudService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.cart.entity.Cart;
import com.project1.domain.shopping.cart.entity.CartItem;
import com.project1.domain.shopping.cart.service.layer2.CartCrudService;
import com.project1.domain.shopping.order.dto.OrderItemDto;
import com.project1.domain.shopping.order.entity.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService{
    private final OrderCrudService crudService;
    private final MemberVerifyService memberVerifyService;
    private final CartCrudService cartCrudService;
    private final ItemCrudService itemCrudService;
    private final DeliveryInfoService deliveryInfoService;

    public OrderService(OrderCrudService crudService, MemberVerifyService memberVerifyService, CartCrudService cartCrudService, ItemCrudService itemCrudService, DeliveryInfoService deliveryInfoService) {
        this.crudService = crudService;
        this.memberVerifyService = memberVerifyService;
        this.cartCrudService = cartCrudService;
        this.itemCrudService = itemCrudService;
        this.deliveryInfoService = deliveryInfoService;
    }
    @Transactional
    public OrderDto.ResponseDto createOrder(OrderDto.PostDto postDto)   {
        Member member = memberVerifyService.findTokenMember();
        DeliveryInfo info = deliveryInfoService.create(postDto,member );
        List<Item> itemList = getItemList(postDto);
        Cart cart = verifyMemberAndCart();

        Order order = crudService.create(postDto,cart,member, info,itemList);

        /*fixme:
            The entire ordering process works in this method. This makes it difficult to change the appropriate order status.
            Also, If an exception occurs in the middle stage, it is difficult to catch.
            For various reasons, including the SRP, this method must be separated.
            ex: order_placed, payment_wait, payment_complete,  delivery_preparing ...
         */
        checkStock(order);

              /*
            PayInfo payInfo = orderPostDto.getPayInfo();
            todo: 결제 진행
         */

        deleteItemInCart(order,cart);
        return crudService.entityToResponse(order);
    }

    public OrderDto.ResponseDto updateStatus(long orderNumber, Order.Status status) {
        Order order = crudService.find(orderNumber);
        order.setStatus(status);

        return crudService.entityToResponse(order);
    }
    public OrderDto.ResponseDto updateDeliveryInfo(long orderNumber, OrderDto.PatchDto patchDto) {
        Order order = crudService.update(orderNumber, patchDto);
        return crudService.entityToResponse(order);
    }
    public void cancelOrder(long orderNumber)  {
        // TODO : 구현필요
    }
    public Page<OrderDto.ResponseDto> findMyOrders(int page, int size) {
        Member member = memberVerifyService.findTokenMember();
        return crudService.findByName(member.getName(),page,size);
    }
    public OrderDto.ResponseDto findOrderDetails(long orderNumber) {
        Order order = crudService.find(orderNumber);
        return crudService.entityToResponse(order);
    }

    /* private */
    private void checkStock(Order order) {
            for(OrderItem orderItem :order.getOrderItemList()){
                itemCrudService.removeStocks(orderItem.getItem(), orderItem.getCount()); // When out of stock,  throw an exception.
            }
    }
    private void deleteItemInCart(Order order, Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        Map<Long, CartItem> cartItemMap = cartItems.stream()
                .collect(Collectors.toMap(
                        item -> item.getItem().getItemId(),
                        item -> item));

        List<CartItem> itemsToRemove = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItemList()) {
            CartItem cartItem = cartItemMap.get(orderItem.getItem().getItemId());

            if (cartItem != null) {
                long cartCount = cartItem.getCount();
                long orderCount = orderItem.getCount();

                if (cartCount == orderCount)  itemsToRemove.add(cartItem);
                else  cartItem.addCount(-orderCount);
            }
        }

        cartItems.removeAll(itemsToRemove);
        order.setCart(cart);
    }
    private Cart verifyMemberAndCart() {
        Member member = memberVerifyService.findTokenMember();
        Cart cart = cartCrudService.findCartByMember(member);

        if (cart.getMember().equals(member)) {
            return cart;
        } else {
            throw new BusinessLogicException(ExceptionCode.CART_NOT_FOUND);
        }
    }
    @NotNull
    private List<Item> getItemList(OrderDto.PostDto postDto) {
        List<Item> itemList = new ArrayList<>();
        for (OrderItemDto item : postDto.getOrderItemList()) {
            itemList.add(itemCrudService.findEntity(item.getItemId()));
        }
        return itemList;
    }

}
