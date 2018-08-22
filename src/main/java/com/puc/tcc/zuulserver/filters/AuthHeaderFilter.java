package com.puc.tcc.zuulserver.filters;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.puc.tcc.zuulserver.http.PermissionCheck;

public class AuthHeaderFilter extends ZuulFilter {
	
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
    	RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        //String token = request.getHeader("x-access-token");
        
        String previousUri = request.getRequestURI();
        if(previousUri.contains("oauth")) {
        	return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        String token = request.getHeader("x-access-token");
        
        String previousUri = request.getRequestURI();
        
        if (StringUtils.isNotBlank(token) && previousUri != null) {
        	
        	boolean permission = PermissionCheck.verifyToken(previousUri, token);

        	if(!permission) {
        		 ctx.setResponseStatusCode(401);
                 ctx.setSendZuulResponse(false);
        	}
        }

        return null;
    }
}