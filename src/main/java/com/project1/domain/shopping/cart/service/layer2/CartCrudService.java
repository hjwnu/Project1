package com.project1.domain.shopping.cart.service.layer2;

import com.project1.domain.shopping.cart.repository.CartRepository;
import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.cart.dto.CartDto;
import com.project1.domain.shopping.cart.entity.Cart;
import com.project1.domain.shopping.cart.mapper.CartMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CartCrudService
    extends GenericCrudService.GenericCrud<Cart, CartDto.Post, CartDto.Response, CartDto.Patch, Long> {
    private final CartRepository repository;
    private final CartMapper mapper;

    public CartCrudService(CartRepository repository, CartMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Cart, Long> getRepository() {
        return repository;
    }

    @Override
    protected void setId(Cart newEntity, Long aLong) {
        newEntity.setCartId(aLong);
    }

    @Override
    protected GenericMapper<Cart, CartDto.Post, CartDto.Response, CartDto.Patch> getMapper() {
        return mapper;
    }
    public Cart findCartByMember(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return repository.findCartByMember(member).orElseGet(() ->save(cart));
    }
}
