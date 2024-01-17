package com.project1.domain.shopping.payment.controller;

import com.project1.domain.shopping.order.dto.OrderDto;
import com.project1.domain.shopping.payment.dto.KakaoPayDto;
import com.project1.domain.shopping.payment.service.layer2.KakaoPayService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    //fixme: If multiple payment functions are needed, planned to be implemented as a strategy pattern.
    @PostMapping("/ready")
    public ResponseEntity<SingleResponseDto<KakaoPayDto.ReadyResponse>> readyToKakaoPay(OrderDto.ResponseDto order) {
        SingleResponseDto<KakaoPayDto.ReadyResponse> response = new SingleResponseDto<>(kakaoPayService.kakaoPayReady(order));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/success")
    public ResponseEntity<SingleResponseDto<KakaoPayDto.ApproveResponse>> afterPayRequest(@RequestParam("pg_token") String pgToken) {
        SingleResponseDto<KakaoPayDto.ApproveResponse> kakaoApprove =
                new SingleResponseDto<>(kakaoPayService.approveResponse(pgToken));
        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
    @PostMapping("/refund")
    public ResponseEntity<SingleResponseDto<KakaoPayDto.CancelResponse>> refund(Long orderNumber) {
        SingleResponseDto<KakaoPayDto.CancelResponse> kakaoCancelResponse =
                new SingleResponseDto<>(kakaoPayService.kakaoCancel(orderNumber));
        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
    @GetMapping("/cancel")
    public void cancel() {
        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
    }
    @GetMapping("/fail")
    public void fail() {
        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
    }
}