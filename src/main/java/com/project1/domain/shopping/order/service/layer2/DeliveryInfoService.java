package com.project1.domain.shopping.order.service.layer2;

import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.entity.DeliveryInfo;
import com.project1.domain.shopping.order.repository.DeliveryInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoService {
    private final DeliveryInfoRepository repository;

    public DeliveryInfoService(DeliveryInfoRepository repository) {
        this.repository = repository;
    }

    public DeliveryInfo create(OrderDto.PostDto postDto, Member member) {
        String recipient = postDto.isSameMemberAndRecipient() ? member.getName() : postDto.getRecipient();
        String address = postDto.isSameMemberAndRecipient() ? member.getAddress() : postDto.getAddress();
        String phone = postDto.isSameMemberAndRecipient() ? member.getPhone() : postDto.getPhone();

        return repository.save(new DeliveryInfo().createDeliverInfo(recipient,phone,address));
    }
}
