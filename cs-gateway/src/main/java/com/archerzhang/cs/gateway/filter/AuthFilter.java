package com.archerzhang.cs.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权过滤器
 * @author archerzhang
 * @date 2019.10.18
 */
@Component
@Slf4j
public class AuthFilter extends ZuulFilter {

    /**
     *  四种类型：pre,routing,error,post
     *     pre：主要用在路由映射的阶段是寻找路由映射表的
     *     routing:具体的路由转发过滤器是在routing路由器，具体的请求转发的时候会调用
     *     error:一旦前面的过滤器出错了，会调用error过滤器。
     *     post:当routing，error运行完后才会调用该过滤器，是在最后阶段的
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 控制过滤器生效不生效，可以在里面写一串逻辑来控制
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 执行过滤逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if (token == null){
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            context.setResponseBody("unAuthrized");
            return null;
        }
        return null;
    }
}
