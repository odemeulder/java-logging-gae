package com.odm.app;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Priority(1)
public class LoggingFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
  private static String REQUEST_HEADER = "X_REQUEST_ID";
  public static String REQUEST_ID_MARKER = "ODM_TRACE_ID";
  
  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
    log.info("filtering ...");
    String requestId = setRequestIdFromRequest(req);

    if(req instanceof HttpServletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        log.info("--> {} {} : {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL(), httpServletRequest.getQueryString());
    }

    filterChain.doFilter(req, resp);

    setRequestIdInResponse(resp, requestId);

    if(resp instanceof HttpServletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        log.info("<-- {} : {}", httpServletResponse.getStatus(), httpServletResponse.toString());
    }
  }

  private String setRequestIdFromRequest(ServletRequest servletRequest){
    String requestId = null;
    if(servletRequest instanceof HttpServletRequest){
      requestId = ((HttpServletRequest) servletRequest).getHeader(REQUEST_HEADER);
    }
    if (requestId == null || requestId.isEmpty()) {
      requestId = UUID.randomUUID().toString();
    } 
    MDC.put(REQUEST_ID_MARKER, requestId);
    return requestId;
  }

  private void setRequestIdInResponse(ServletResponse servletResponse, String requestId){
    if (requestId != null && servletResponse instanceof HttpServletResponse) {
        ((HttpServletResponse)servletResponse).addHeader(REQUEST_HEADER, requestId);
    }
  }
}