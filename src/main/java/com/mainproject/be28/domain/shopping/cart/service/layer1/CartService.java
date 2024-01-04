package com.mainproject.be28.domain.shopping.cart.service.layer1;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.service.layer2.CartCrudService;
import com.mainproject.be28.domain.shopping.cart.service.layer2.CartItemService;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.layer2.ItemCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j
public class CartService{
    private final CartCrudService crudService;
    private final CartItemService cartItemService;
    private final MemberVerifyService memberVerifyService;
    private final ItemCrudService itemCrudService;

    public CartService(CartCrudService crudService, CartItemService cartItemService, MemberVerifyService memberVerifyService, ItemCrudService itemCrudService) {
        this.crudService = crudService;
        this.cartItemService = cartItemService;
        this.memberVerifyService = memberVerifyService;
        this.itemCrudService = itemCrudService;
    }


    @Transactional
    public CartDto.Response addCart(CartItemDto cartItemDto) {
    Cart cart = findCartByMember();
    CartItem cartItem = cartItemService.findCartItem(cart.getCartId(), cartItemDto.getItemId());
    if (cartItem.getCartItemId() == null) {
        cartItem = getNewCartItem(cartItemDto, cart);
    }
    cartItem.addCount(cartItemDto.getCount());

    addItemInCart(cart, cartItem);
    crudService.save(cart);

    return crudService.entityToResponse(cart);
    }


    public void removeItem(long itemId) { // 장바구니 내 개별 상품 제거
        Cart cart = findCartByMember();
        CartItem cartItem = cartItemService.verifyExistCartItem(cart.getCartId(), itemId);
        cartItemService.delete(cartItem.getCartItemId());
    }

    public void removeAllItem() { // 장바구니 전체 삭제
        Cart cart = findCartByMember();
        crudService.delete(cart.getCartId());
    }

    public Cart findCartByMember() {
        Member member =  memberVerifyService.findTokenMember();
        return crudService.findCartByMember(member);
    }



    private void addItemInCart(Cart cart, CartItem cartItem) {
        List<CartItem> cartItemList = cart.getCartItems();
        if (!cartItemList.contains(cartItem)) {
            cartItemList.add(cartItemService.save(cartItem));
        }

        cart.setCartItems(cartItemList);
    }

    private CartItem getNewCartItem(CartItemDto cartItemDto, Cart cart) {
        Item item = itemCrudService.findEntity(cartItemDto.getItemId());
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(0L);
        return cartItem;
    }
}
