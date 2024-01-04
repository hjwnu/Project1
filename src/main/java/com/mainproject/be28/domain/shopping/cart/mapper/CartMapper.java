package com.mainproject.be28.domain.shopping.cart.mapper;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemResponseDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.global.generic.GenericMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
@Mapper(componentModel = "spring")
public interface CartMapper
    extends GenericMapper<Cart, CartDto.Post, CartDto.Response, CartDto.Patch, Long> {


    default CartDto.Response cartToCartResponseDto(Cart cart) {

        List<CartItemResponseDto> cartItemResponseDtos = getCartItemsResponseDto(cart);
        long price = getTotalPrice(cartItemResponseDtos);
        return new CartDto.Response(cartItemResponseDtos, price);
    }

    default long getTotalPrice(List<CartItemResponseDto> cartItemResponseDtos) {
        long price = 0;
        for (CartItemResponseDto cartItemResponseDto : cartItemResponseDtos) {
            price += cartItemResponseDto.getPrice() * cartItemResponseDto.getCount();
        }
        return price;
    }


    default List<CartItemResponseDto> getCartItemsResponseDto(Cart cart) {
        List<CartItemResponseDto> cartItemDtos = new ArrayList<>();
        if (cart == null) {
            return new ArrayList<>();
        }
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getCount() != 0) {
                    CartItemResponseDto cartItemDto =
                            CartItemResponseDto.builder()
                                    .cartItemId(cartItem.getCartItemId())
                                    .itemId(cartItem.getItem().getItemId())
                                    .count(cartItem.getCount())
                                    .name(cartItem.getItem().getName())
                                    .price(cartItem.getItem().getPrice())
                                    .build();
                    cartItemDtos.add(cartItemDto);
                }
            }
        }
        return cartItemDtos;
    }
}
