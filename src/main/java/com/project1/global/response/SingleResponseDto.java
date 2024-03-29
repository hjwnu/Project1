package com.project1.global.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public final class SingleResponseDto<T> extends BaseResponse{
    private T data;
    public SingleResponseDto(T data, HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }
}

