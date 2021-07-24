package com.assignment.User.Interceptor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.debug("Inside preHandle of LoggerInterceptor");
        logger.debug("-------------------Request Details----------------");
        logger.debug("RequestURL::"+request.getRequestURL().toString());
        logger.debug("ContentType::"+request.getContentType());
        logger.debug("Method::"+request.getMethod());
        ArrayList<String> requestHeadersList = Collections.list(request.getHeaderNames());
        List<String> requestHeadersListValues = requestHeadersList.stream().map(s -> request.getHeader(s)).collect(Collectors.toList());
        Map<String, String> requestHeadersMap = IntStream.range(0, requestHeadersList.size())
                .boxed()
                .collect(Collectors.toMap(i -> requestHeadersList.get(i), i -> requestHeadersListValues.get(i)));
        logger.debug("RequestHeaders::"+ requestHeadersMap);
        logger.debug("RequestBody"+ IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("Inside postHandle of LoggerInterceptor");
        logger.debug("-------------------Response Details----------------");
        logger.debug("ContentType::"+response.getContentType());
        logger.debug("Status::"+response.getStatus());
        ArrayList<String> responseHeadersList = new ArrayList<>(response.getHeaderNames());
        List<String> responseHeadersListValues = responseHeadersList.stream().map(s -> response.getHeader(s)).collect(Collectors.toList());
        Map<String, String> responseHeadersMap = IntStream.range(0, responseHeadersList.size())
                .boxed()
                .collect(Collectors.toMap(i -> responseHeadersList.get(i), i -> responseHeadersListValues.get(i)));
        logger.debug("ResponseHeaders::"+ responseHeadersMap);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
     HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
