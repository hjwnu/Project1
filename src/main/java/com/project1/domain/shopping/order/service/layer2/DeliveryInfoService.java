package com.project1.domain.shopping.order.service.layer2;

import com.project1.domain.member.auth.jwt.JwtTokenizer;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.entity.DeliveryInfo;
import com.project1.domain.shopping.order.repository.DeliveryInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoService {
    private final DeliveryInfoRepository repository;
    private final JwtTokenizer jwtTokenizer;

    public DeliveryInfoService(DeliveryInfoRepository repository, JwtTokenizer jwtTokenizer) {
        this.repository = repository;
        this.jwtTokenizer = jwtTokenizer;
    }

    public DeliveryInfo create(OrderDto.PostDto postDto, Member member) {
        String recipient = postDto.isSameMemberAndRecipient() ?member.getName() : postDto.getRecipient();
        String address = postDto.isSameMemberAndRecipient() ?  member.getAddress() : jwtTokenizer.dataEnDecrypt(postDto.getAddress(),1);
        String phone = postDto.isSameMemberAndRecipient() ? member.getPhone() : jwtTokenizer.dataEnDecrypt(postDto.getPhone(),1);
        return repository.save(new DeliveryInfo().createDeliverInfo(recipient,phone,address));
    }
}
