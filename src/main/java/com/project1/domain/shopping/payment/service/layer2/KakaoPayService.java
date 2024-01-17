package com.project1.domain.shopping.payment.service.layer2;

import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.entity.Order;
import com.project1.domain.shopping.order.entity.OrderItem;
import com.project1.domain.shopping.order.service.layer2.OrderCrudService;
import com.project1.domain.shopping.payment.dto.KakaoPayDto;
import com.project1.domain.shopping.payment.service.layer3.PayInfoCrudService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class KakaoPayService {
    private static final String CID = "TC0ONETIME";
    private static final String URL = "ec2-43-201-245-208.ap-northeast-2.compute.amazonaws.com:8080";
    private static final String ADMIN_KEY = "${ADMIN_KEY}";
    private KakaoPayDto.ReadyResponse ready;
    private String partnerOrderId;
    private String partnerUserId;

    private final OrderCrudService orderCrudService;
    private final PayInfoCrudService payInfoCrudService;
    public KakaoPayService(OrderCrudService orderCrudService, PayInfoCrudService payInfoCrudService) {
        this.orderCrudService = orderCrudService;
        this.payInfoCrudService = payInfoCrudService;
    }

    public KakaoPayDto.ReadyResponse kakaoPayReady(OrderDto.ResponseDto order) {
        Order findOrder = orderCrudService.find(order.getOrderNumber());
        List<OrderItem> itemList = findOrder.getOrderItemList();
        String itemName =  itemList.get(0).getItem().getName();
        partnerOrderId = findOrder.getOrderId().toString();
        partnerUserId = findOrder.getMember().getName();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CID);
        parameters.add("partner_order_id", partnerOrderId);
        parameters.add("partner_user_id", partnerUserId);
        parameters.add("item_name", itemList.size() == 1? itemName: itemName + " 외 " + (itemList.size()-1) + "개");
        parameters.add("quantity",  findOrder.getOrderItemList().stream().map(OrderItem::getCount).reduce(0L,Long::sum).toString());
        parameters.add("total_amount", String.valueOf(findOrder.getTotalPrice()));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url",URL+"/payment/success");
        parameters.add("cancel_url", URL +"/payment/cancel");
        parameters.add("fail_url", URL+"/payment/fail");

// 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters,this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        ready = restTemplate.postForObject(
                 "https://kapi.kakao.com/v1/payment/ready",
                 requestEntity,
                 KakaoPayDto.ReadyResponse.class);
        payInfoCrudService.create(findOrder, ready);
        return ready;
    }
    public KakaoPayDto.ApproveResponse approveResponse(String pgToken) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CID);
        parameters.add("tid", ready.getTid());
        parameters.add("partner_order_id", partnerOrderId);
        parameters.add("partner_user_id", partnerUserId);
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoPayDto.ApproveResponse.class);
    }
    public KakaoPayDto.CancelResponse kakaoCancel(Long orderNumber) {
        Order order = orderCrudService.find(orderNumber);
        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", CID);
        parameters.add("tid", order.getPayInfo().getTid());
        parameters.add("cancel_amount", String.valueOf(order.getTotalPrice()));
        parameters.add("cancel_tax_free_amount", "0");
        parameters.add("cancel_vat_amount", "0");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoPayDto.CancelResponse.class);
    }
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + ADMIN_KEY);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}
