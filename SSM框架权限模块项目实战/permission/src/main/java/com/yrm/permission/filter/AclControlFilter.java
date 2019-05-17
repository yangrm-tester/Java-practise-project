package com.yrm.permission.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.yrm.permission.common.ApiResult;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.entity.SysUser;
import com.yrm.permission.service.SysRoleService;
import com.yrm.permission.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AclControlFilter
 * @createTime 2019年05月14日 17:27:00
 */
@Component
public class AclControlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AclControlFilter.class);

    private static final String EXCLUSIONURLS = "exclusionUrls";

    /**
     * 后台请求url后缀
     */
    private static final String REQUEST_URL_SUFFIX = ".json";
    /**
     * 创建线程安全的hashSet实例
     */
    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    /**
     * 没有权限页面
     **/
    private final static String noAuthUrl = "/sys/user/noAuth.page";


    private SysRoleService sysRoleService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //filter初始化拿到参数值 已经配置好了
        String exclusionUrls = filterConfig.getInitParameter(EXCLUSIONURLS);
        //转list
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        /**白名单地址，这些地址不校验的 其实login.page 不拦截的 但是login.page 地址会重定向到主页上去 /admin/page url
         * /sys/user/noAuth.page,/login.page
         * */
        exclusionUrlSet.add(noAuthUrl);
        /**
         * 获得sysRoleService实例
         * */
        ServletContext sc = filterConfig.getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if (cxt != null && cxt.getBean(SysRoleService.class) != null && sysRoleService == null) {
            sysRoleService = cxt.getBean(SysRoleService.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //做业务上的权限拦截
        //1.如果请求的url在exclusionUrlSet中则放行
        //返回Servlet的名称或Servlet所映射的路径。获取网络连接信息
        String requestUrl = request.getServletPath();
        if (exclusionUrlSet.contains(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        //获得用户信息
        SysUser sysUser = RequestHolder.getSysUser();
        if (sysUser == null) {
            logger.info("request url ===>{},but not login", requestUrl);
            noAuth(request, response);
            return;
        }
        //判断用户是否有这个权限点
        try {
            if (!sysRoleService.hasUrlAcl(requestUrl)) {
                logger.info("request url ===>{},but has no permission", requestUrl);
                noAuth(request, response);
                return;
            }
        } catch (Exception e) {
            logger.error("user get acl error,Errors===>{}", e.getMessage());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getServletPath();
        if (requestUrl.endsWith(REQUEST_URL_SUFFIX)) {
            ApiResult apiResult = ApiResult.failResult("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonUtil.obj2String(apiResult));
        } else {
            clientRedirect(noAuthUrl, response);
        }
    }

    private void clientRedirect(String noAuthUrl, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + noAuthUrl + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }

}
