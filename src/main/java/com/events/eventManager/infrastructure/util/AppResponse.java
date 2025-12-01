package com.events.eventManager.infrastructure.util;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppResponse<T>(
    String status,
    T data,
    Meta meta
) {

    public static <T> AppResponse<T> ok(T data) {
        return new AppResponse<>("success", data, null);
    }

    public static <T> AppResponse<T> withMeta(T data, Meta meta) {
        return new AppResponse<>("success", data, meta);
    }


    public static <T> AppResponse<T> withMessage(T data, String message) {
        String traceId = Trace.currentId();
        Meta meta = new Meta(message, traceId, "v1", null);
        return new AppResponse<>("success", data, meta);
    }


    public static <T> AppResponse<T> withPagination(T data, Pagination page) {
        String traceId = Trace.currentId();
        Meta meta = new Meta(null, traceId, "v1", page);
        return new AppResponse<>("success", data, meta);
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Meta(
        String message,
        String traceId,
        String version,
        Pagination page
    ) {}


    public record Pagination(
        int page,
        int size,
        long totalElements,
        int totalPages
    ) {
        public static Pagination from(org.springframework.data.domain.Page<?> springPage) {
            return new Pagination(
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages()
            );
        }
    }
}