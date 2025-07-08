package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> Response<T> ok(T data) {
        return new Response<>(true, "Thành công", data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(false, message, null);
    }
}
