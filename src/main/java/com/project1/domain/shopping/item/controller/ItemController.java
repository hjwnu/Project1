package com.project1.domain.shopping.item.controller;

import com.project1.domain.shopping.item.service.layer1.ItemService;
import com.project1.global.response.MultiResponseDto;
import com.project1.global.response.SingleResponseDto;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.dto.ItemSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;
    private final HttpStatus ok = HttpStatus.OK;
    @GetMapping("/{item-id}")
    public ResponseEntity<SingleResponseDto<ItemDto.ResponseWithReview>> getItem(@PathVariable("item-id") @Positive long itemId){
        ItemDto.ResponseWithReview item = itemService.findItem(itemId);
        SingleResponseDto<ItemDto.ResponseWithReview> response = new SingleResponseDto<>(item, ok);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping( "s")
    public ResponseEntity<MultiResponseDto<ItemDto.ResponseDtoWithoutReview>> getItems(
            @RequestParam(defaultValue = "1") int page,
            @RequestBody @Valid ItemSearchCondition itemSearchCondition){

        Page<ItemDto.ResponseDtoWithoutReview> items = itemService.findItems(page, itemSearchCondition);

        MultiResponseDto<ItemDto.ResponseDtoWithoutReview> response = new MultiResponseDto<>(items,ok);
        return new ResponseEntity<>(response, ok);
    }



}
