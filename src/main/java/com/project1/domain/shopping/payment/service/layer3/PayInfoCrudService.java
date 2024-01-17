package com.project1.domain.shopping.payment.service.layer3;

import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.payment.dto.KakaoPayDto;
import com.project1.domain.shopping.payment.entity.PayInfo;
import com.project1.domain.shopping.payment.repository.PayInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class PayInfoCrudService {
    private final PayInfoRepository repository;

    public PayInfoCrudService(PayInfoRepository repository) {
        this.repository = repository;
    }

    public PayInfo create(Order order, KakaoPayDto.ReadyResponse readyResponse) {
        PayInfo payInfo = PayInfo.builder().tid(readyResponse.getTid()).order(order).build();
        return repository.save(payInfo);
    }

    public PayInfo find(Long orderNumber) {
        return repository.findPayInfoByOrder_OrderId(orderNumber);
    }
}
