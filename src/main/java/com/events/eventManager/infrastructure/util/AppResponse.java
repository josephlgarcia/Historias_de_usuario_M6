package com.events.eventManager.infrastructure.util;


public record AppResponse<T> (
    String status,
    T data,
    Meta meta){

        public static <T> AppResponse<T> ok(T data) {return new AppResponse<>("success", data, null);}

        public static <T> AppResponse<T> withMeta(T data, Meta meta) { return new AppResponse<>("success", data, meta); }

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
        ) {}
    }

