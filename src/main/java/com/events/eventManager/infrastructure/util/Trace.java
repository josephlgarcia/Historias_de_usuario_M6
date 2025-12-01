package com.events.eventManager.infrastructure.util;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.MDC;

public final class Trace {
    
    private static final String TRACE_ID_KEY = "traceId";
    
    private Trace() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String currentId() {
        return Optional.ofNullable(MDC.get(TRACE_ID_KEY))
                .orElseGet(() -> {
                    String newTraceId = UUID.randomUUID().toString();
                    MDC.put(TRACE_ID_KEY, newTraceId);
                    return newTraceId;
                });
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static void clear() {
        MDC.remove(TRACE_ID_KEY);
    }
}
