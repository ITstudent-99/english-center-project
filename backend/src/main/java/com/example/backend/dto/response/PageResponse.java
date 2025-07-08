package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends Response<List<T>>{
    private int totalPages;
    private int pageIndex;
    private int pageSize;
    private long totalElements;

    public PageResponse(boolean success, String message, List<T> data,
                        int totalPages, int pageIndex, int pageSize, long totalElements) {
        super(success, message, data);
        this.totalPages = totalPages;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public static <T> PageResponse<T> of(List<T> data, int totalPages,
                                         int pageIndex, int pageSize, long totalElements) {
        return new PageResponse<>(true, "Thành công", data, totalPages,
                pageIndex, pageSize, totalElements);
    }
}
