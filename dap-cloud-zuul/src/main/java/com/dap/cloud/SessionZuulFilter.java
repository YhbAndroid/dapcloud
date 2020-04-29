package com.dap.cloud;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class SessionZuulFilter extends ZuulFilter {

    @Value("${system.config.sessionZuulFilter.ignore}")
    private String ignoreUrl;

    /**
     * 过滤器类型 pre 事前 routing 路由请求时候调用 error 发生错误时候调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否过来 0 不过滤 1 过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
        String requestUri = req.getRequestURI().toString();
        String[] ignoreUrlArr = ignoreUrl.split(",");
        //System.err.println(req.getSession().getAttribute("currentUserName"));
        //System.err.println(requestUri);
        for (String url : ignoreUrlArr) {
            String mainRegex = url.replaceAll("\\*\\*", ".*");
            if (requestUri.matches(mainRegex)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拦截的具体操作 验证token
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String loginUrl = request.getContextPath() + "/system";
        Object userName = request.getSession().getAttribute("currentUserName");
        if (userName == null || userName.toString().equals("")) {
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            //ctx.setResponseBody("{\"result\":\"username is not correct!\"}");// 返回错误内容
            String str = "<script language='javascript'>alert('会话过期,请重新登录');"
                    + "window.top.location.href='"
                    + loginUrl
                    + "';</script>";
            ctx.setResponseBody(str);
            response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
            ctx.set("isSuccess", false);
            return null;
        } else {
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            //ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
            return null;
        }
    }
}