package com.events.eventManager.infrastructure.config;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.events.eventManager.infrastructure.util.Trace;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TraceInterceptor implements HandlerInterceptor{
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {
        
        String traceId = request.getHeader(TRACE_ID_HEADER);
        
        if (traceId != null && !traceId.isBlank()) {
            Trace.setTraceId(traceId);
        } else {
            traceId = Trace.currentId();
        }
        
        response.setHeader(TRACE_ID_HEADER, traceId);
        
        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex) {
        
        Trace.clear();
    }
}
