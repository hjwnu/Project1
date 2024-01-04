package com.mainproject.be28.domain.shopping.cart.service.layer2;

import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemResponseDto;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.repository.CartItemRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.generic.GenericCrudService;
import com.mainproject.be28.global.generic.GenericMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CartItemService
        extends GenericCrudService.GenericCrud<CartItem, CartItemDto, CartItemDto, CartItemResponseDto, Long> {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
 // save, delete만 사용

    public CartItem findCartItem(long cartId, long itemId) {
        return cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cartId, itemId).orElse(new CartItem());
    }
    public CartItem verifyExistCartItem(long cartId, long itemId) {
        return cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cartId, itemId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_ITEM_NOT_FOUND));
    }
    @Override
    protected JpaRepository<CartItem, Long> getRepository() {
        return cartItemRepository;
    }

    @Override
    protected void setId(CartItem newEntity, Long aLong) {
        newEntity.setCartItemId(aLong);
    }

    @Override
    protected GenericMapper<CartItem, CartItemDto, CartItemDto, CartItemResponseDto, Long> getMapper() {
        return null;
    }

    @Override
    protected List<CartItem> findByName(String str) {
        return null;
    }
}
