package com.project1.domain.shopping.cart.mapper;

import com.project1.domain.shopping.cart.dto.CartDto.Patch;
import com.project1.domain.shopping.cart.dto.CartDto.Post;
import com.project1.domain.shopping.cart.dto.CartDto.Response;
import com.project1.domain.shopping.cart.dto.CartItemDto;
import com.project1.domain.shopping.cart.dto.CartItemResponseDto;
import com.project1.domain.shopping.cart.dto.CartItemResponseDto.CartItemResponseDtoBuilder;
import com.project1.domain.shopping.cart.entity.Cart;
import com.project1.domain.shopping.cart.entity.CartItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T09:50:34+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public Cart postDtoToEntity(Post postDto) {
        if ( postDto == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setCartItems( cartItemDtoListToCartItemList( postDto.getCartItems() ) );

        return cart;
    }

    @Override
    public Cart patchDtoToEntity(Patch patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setCartItems( cartItemDtoListToCartItemList( patchDto.getCartItems() ) );

        return cart;
    }

    @Override
    public Response entityToResponseDto(Cart entity) {
        if ( entity == null ) {
            return null;
        }

        List<CartItemResponseDto> cartItems = null;
        long totalPrice = 0L;

        cartItems = cartItemListToCartItemResponseDtoList( entity.getCartItems() );
        totalPrice = entity.getTotalPrice();

        Response response = new Response( cartItems, totalPrice );

        return response;
    }

    protected CartItem cartItemDtoToCartItem(CartItemDto cartItemDto) {
        if ( cartItemDto == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setCount( cartItemDto.getCount() );

        return cartItem;
    }

    protected List<CartItem> cartItemDtoListToCartItemList(List<CartItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItem> list1 = new ArrayList<CartItem>( list.size() );
        for ( CartItemDto cartItemDto : list ) {
            list1.add( cartItemDtoToCartItem( cartItemDto ) );
        }

        return list1;
    }

    protected CartItemResponseDto cartItemToCartItemResponseDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemResponseDtoBuilder cartItemResponseDto = CartItemResponseDto.builder();

        if ( cartItem.getCartItemId() != null ) {
            cartItemResponseDto.cartItemId( cartItem.getCartItemId() );
        }
        cartItemResponseDto.count( cartItem.getCount() );

        return cartItemResponseDto.build();
    }

    protected List<CartItemResponseDto> cartItemListToCartItemResponseDtoList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemResponseDto> list1 = new ArrayList<CartItemResponseDto>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemToCartItemResponseDto( cartItem ) );
        }

        return list1;
    }
}
