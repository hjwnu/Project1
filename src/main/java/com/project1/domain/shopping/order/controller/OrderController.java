package com.project1.domain.shopping.order.controller;

import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.service.layer1.OrderService;
import com.project1.domain.shopping.payment.dto.KakaoPayDto;
import com.project1.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private  final HttpStatus ok = HttpStatus.OK;
    //fixme: If multiple payment functions are needed, planned to be implemented as a strategy pattern.
    @PostMapping("/new-order")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> createOrder(@Valid @RequestBody OrderDto.PostDto postDto) {
        SingleResponseDto<OrderDto.ResponseDto> response =  new SingleResponseDto<>(orderService.createOrder(postDto),ok);
        return new ResponseEntity<>(response, ok);
    }

    @PatchMapping("/{orderNumber}")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> patchOrderInfo(@Valid @RequestParam("orderNumber") long orderNumber
                                                                               ,@Valid @RequestBody OrderDto.PatchDto patchDto) {
        OrderDto.ResponseDto order =
                orderService.updateDeliveryInfo(orderNumber,patchDto);
        SingleResponseDto<OrderDto.ResponseDto> response =  new SingleResponseDto<>(order,ok);

        return new ResponseEntity<>(response, ok);
    }

    @GetMapping("/checkout")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> processApprovePayment(KakaoPayDto.ApproveResponse approve) {
        SingleResponseDto<OrderDto.ResponseDto> response = new SingleResponseDto<>(orderService.afterApprove(approve), ok);
        return new ResponseEntity<>(response, ok);
    }

    @GetMapping()
    public ResponseEntity<SingleResponseDto<Page<OrderDto.ResponseDto>>> getOrderByMember(int page, int size) {
        SingleResponseDto<Page<OrderDto.ResponseDto>> response =  new SingleResponseDto<>(orderService.findMyOrders(page,size),ok);
        return new ResponseEntity<>(response, ok);
    }

    @PostMapping("/refund")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> refundOrder(KakaoPayDto.CancelResponse cancel) {
        SingleResponseDto<OrderDto.ResponseDto> response = new SingleResponseDto<>(orderService.cancelOrder(cancel),ok);
        return new ResponseEntity<>(response,ok);
    }
}

