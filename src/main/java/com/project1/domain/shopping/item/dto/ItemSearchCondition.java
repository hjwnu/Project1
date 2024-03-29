package com.project1.domain.shopping.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchCondition {

    @Nullable
    private String category;
    @Nullable
    private String brand;
    @Nullable
    private String color;
    @Nullable
    private Long lowPrice;
    @Nullable
    private Long highPrice;
    @Nullable
    private String name;
    @Nullable
    private String status; // 검색조건 설정 미구현된 필드
    @Nullable
    @Pattern(regexp = "^(score|review|name|price)$", message = "정렬 기준은 score, review, name, price 중에서 입력 되어야 합니다.")
    private String sort;
    @Nullable
    @Pattern(regexp = "^(asc|desc)$", message = "정렬 순서는 asc,desc 중 하나만 입력 되어야 합니다.")
    private String order;

}
