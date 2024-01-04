package com.mainproject.be28.domain.shopping.cart.service.layer2;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.mapper.CartMapper;
import com.mainproject.be28.domain.shopping.cart.repository.CartRepository;
import com.mainproject.be28.global.generic.GenericCrudService;
import com.mainproject.be28.global.generic.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    protected GenericMapper<Cart, CartDto.Post, CartDto.Response, CartDto.Patch, Long> getMapper() {
        return mapper;
    }
    public Cart findCartByMember(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return repository.findCartByMember(member).orElseGet(() ->save(cart));
    }

    @Override // 미사용
    protected List<Cart> findByName(String str) {
        return null;
    }
}
