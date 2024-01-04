package com.mainproject.be28.domain.shopping.order.service.layer1;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.service.layer2.CartCrudService;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.layer2.ItemCrudService;
import com.mainproject.be28.domain.shopping.order.dto.OrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderItemDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import com.mainproject.be28.domain.shopping.order.service.layer2.OrderCrudService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
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

    public OrderService(OrderCrudService crudService, MemberVerifyService memberVerifyService, CartCrudService cartCrudService, ItemCrudService itemCrudService) {
        this.crudService = crudService;
        this.memberVerifyService = memberVerifyService;
        this.cartCrudService = cartCrudService;
        this.itemCrudService = itemCrudService;
    }
    @Transactional
    public OrderDto.ResponseDto createOrder(OrderDto.PostDto postDto)   {
        List<Item> itemList = getItemList(postDto);
        Order order = crudService.create(postDto,memberVerifyService.findTokenMember(),itemList);
        checkStock(order);

              /*
            PayInfo payInfo = orderPostDto.getPayInfo();
            todo: 결제 진행
         */

        deleteItemInCart(order);
        return crudService.entityToResponse(order);
    }
    public void changeStatus(long orderNumber, Order.Status status) {
        crudService.find(orderNumber).setStatus(status);
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
                orderItem.getItem().removeStocks(orderItem.getCount()); // When out of stock,  throw an exception.
            }
    }
    private void deleteItemInCart(Order order) {
        Cart cart = verifyMemberAndCart();
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
