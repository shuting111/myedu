package com.zb.filter;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/11
 * @Version V1.0
 */

@Component
public class RouterFilter extends ZuulFilter {


    private final static RateLimiter RATE_LIMITER = RateLimiter.create(2);
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }


    @Override
    public int filterOrder() {
        return -10;
    }


    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        if(!RATE_LIMITER.tryAcquire()){
            requestContext.getResponse().setContentType("application/json; charset=utf-8");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("{'msg':'访问人数太多，请等待...'}");
            return false;
        }
        return true;
    }


    @Override
    public Object run() {
        return null;
    }
}
