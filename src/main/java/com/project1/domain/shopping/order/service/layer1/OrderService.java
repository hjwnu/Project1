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
import com.project1.domain.shopping.payment.dto.KakaoPayDto;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service @Transactional
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

    private final ConcurrentHashMap<Long, ReentrantLock> orderLock = new ConcurrentHashMap<>();
    @Transactional
    public OrderDto.ResponseDto createOrder(OrderDto.PostDto postDto) {
        MyCart myCart = getMyCart();
        DeliveryInfo deliveryInfo = deliveryInfoService.create(postDto, myCart.getMember());
        List<Item> itemList = getItemList(postDto);
        List<OrderItem> list = orderItemService.orderItemDtoToEntityList(postDto, itemList);

        Order order = crudService.create(myCart.getCart(), myCart.getMember(), deliveryInfo, list);
        Lock lock = orderLock.computeIfAbsent(order.getOrderId(), k -> new ReentrantLock());
        if (lock.tryLock()) {
            try {
                orderItemService.saveAll(order);

                checkStocks(order); // When out of stock,  throw an exception.
                order.setStatus(Order.Status.PAYMENT_WAIT);
                return crudService.entityToResponse(order);
            } finally {
                lock.unlock();
            }
        } else{
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    @Transactional
    public OrderDto.ResponseDto afterApprove(KakaoPayDto.ApproveResponse approve) {
        Lock lock = orderLock.computeIfAbsent(Long.valueOf(approve.getPartner_order_id()), k -> new ReentrantLock());
        if(lock.tryLock()) {
            try {
                Order order = crudService.find(Long.parseLong(approve.getPartner_order_id()));
                deleteItemInCart(order, getMyCart().getCart());
                order.setStatus(Order.Status.PAYMENT_COMPLETED);
                return crudService.entityToResponse(order);
            }
            finally {
                lock.unlock();
            }
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public OrderDto.ResponseDto updateDeliveryInfo(long orderNumber, OrderDto.PatchDto patchDto) {
        Order order = crudService.update(orderNumber, patchDto);
        return crudService.entityToResponse(order);
    }
    public OrderDto.ResponseDto cancelOrder(KakaoPayDto.CancelResponse cancelResponse)  {
        long orderNumber = Long.parseLong(cancelResponse.getPartner_order_id());
        Order order = crudService.find(orderNumber);
        recoveryStocks(order);
        order.setStatus(Order.Status.ORDER_CANCELED);
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
    private void checkStocks(Order order) {
            for(OrderItem orderItem :order.getOrderItemList()){
                itemCrudService.checkStocks(orderItem.getItem(), orderItem.getCount()); // When out of stock,  throw an exception.
            }
    }

    private void removeStocks(Order order) {
        for(OrderItem orderItem :order.getOrderItemList()){
            orderItem.getItem().removeStocks(orderItem.getCount());
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
    private Cart verifyMemberAndCart(Member member) {
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

    @NotNull
    private OrderService.MyCart getMyCart() {
        Member member = memberVerificationService.findTokenMember();
        Cart cart = verifyMemberAndCart(member);
        MyCart memberAndCart = new MyCart(member, cart);
        return memberAndCart;
    }
    @Getter @Setter
    private static class MyCart {
        private final Member member;
        private final Cart cart;

        public MyCart(Member member, Cart cart) {
            this.member = member;
            this.cart = cart;
        }
    }
}
