package com.project1.domain.shopping.order.service.layer1;

import com.project1.domain.member.entity.Member;
import com.project1.domain.member.service.Layer2.MemberVerificationService;
import com.project1.domain.shopping.cart.entity.Cart;
import com.project1.domain.shopping.cart.entity.CartItem;
import com.project1.domain.shopping.cart.service.layer2.CartCrudService;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.dto.OrderItemDto;
import com.project1.domain.shopping.order.entity.DeliveryInfo;
import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.domain.shopping.order.service.layer2.DeliveryInfoService;
import com.project1.domain.shopping.order.service.layer2.OrderCrudService;
import com.project1.domain.shopping.order.service.layer2.OrderItemService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
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
    private final OrderItemService orderItemService;
    private final MemberVerificationService memberVerificationService;
    private final CartCrudService cartCrudService;
    private final ItemCrudService itemCrudService;
    private final DeliveryInfoService deliveryInfoService;

    public OrderService(OrderCrudService crudService, OrderItemService orderItemService, MemberVerificationService memberVerificationService, CartCrudService cartCrudService, ItemCrudService itemCrudService, DeliveryInfoService deliveryInfoService) {
        this.crudService = crudService;
        this.orderItemService = orderItemService;
        this.memberVerificationService = memberVerificationService;
        this.cartCrudService = cartCrudService;
        this.itemCrudService = itemCrudService;
        this.deliveryInfoService = deliveryInfoService;
    }
    @Transactional
    public OrderDto.ResponseDto createOrder(OrderDto.PostDto postDto)   {
        Member member = memberVerificationService.findTokenMember();
        Cart cart = verifyMemberAndCart();
        DeliveryInfo info = deliveryInfoService.create(postDto,member);
        List<Item> itemList = getItemList(postDto);

        List<OrderItem> list = orderItemService.orderItemDtoToEntityList(postDto, itemList);
        Order order = crudService.create(cart,member, info,list);
        orderItemService.saveAll(order);
        /*fixme:
            The entire ordering process works in this method. This makes it difficult to change the appropriate order status.
            Also, If an exception occurs in the middle stage, it is difficult to catch.
            For various reasons, including the SRP, this method must be separated.
            ex: order_placed, payment_wait, payment_complete,  delivery_preparing ...
         */
        checkStock(order);

              /*
            PayInfo payInfo = orderPostDto.getPayInfo();
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
    public OrderDto.ResponseDto cancelOrder(long orderNumber)  {
        Order order = crudService.find(orderNumber);
        order.setStatus(Order.Status.ORDER_CANCELED);
        /* TODO : This must be seperated too. Or delayed.
            Stage : DELIVERY_CANCELED -> (RETURN_PRODUCT) -> REFUND_PROGRESS
         */
        recoveryStocks(order);

        return crudService.entityToResponse(order);
    }
    public Page<OrderDto.ResponseDto> findMyOrders(int page, int size) {
        return crudService.findByName(memberVerificationService.findTokenMember(),page,size);
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
    private void recoveryStocks(Order order) {
        for(OrderItem orderItem : order.getOrderItemList()){
            Item item = orderItem.getItem();
            item.setStock(item.getStock()+orderItem.getCount());
            itemCrudService.save(item);
        }
    }
    private Cart verifyMemberAndCart() {
        Member member = memberVerificationService.findTokenMember();
        Cart cart = cartCrudService.findCartByMember(member);

        if (cart.getMember().equals(member)) return cart;
        else  throw new BusinessLogicException(ExceptionCode.CART_NOT_FOUND);
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
