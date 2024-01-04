package com.project1.domain.shopping.order.controller;

import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.order.service.layer1.OrderService;
import com.project1.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private  final HttpStatus ok = HttpStatus.OK;


    @PostMapping("/newOrder")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> postOrder(@Valid @RequestBody OrderDto.PostDto postDto) {
        SingleResponseDto<OrderDto.ResponseDto> response =  new SingleResponseDto<>(orderService.createOrder(postDto),ok);
        return new ResponseEntity<>(response, ok);
    }

    @GetMapping("/checkout")
    public ResponseEntity<SingleResponseDto<OrderDto.ResponseDto>> getOrderToPay(@Positive long orderNumber) { // 결제 창
        SingleResponseDto<OrderDto.ResponseDto> response =  new SingleResponseDto<>(orderService.findOrderDetails(orderNumber),ok);
        return new ResponseEntity<>(response, ok);
    }

    @GetMapping()
    public ResponseEntity<SingleResponseDto<Page<OrderDto.ResponseDto>>> getOrderMember(int page, int size) {

        SingleResponseDto<Page<OrderDto.ResponseDto>> response =  new SingleResponseDto<>(orderService.findMyOrders(page,size),ok);
        return new ResponseEntity<>(response, ok);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("order-id") long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

