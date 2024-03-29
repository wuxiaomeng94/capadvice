package com.capinfo.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class CorsInterceptor implements HandlerInterceptor {
    private List<String> excludedUrls;
    public List<String> getExcludedUrls() {
        return excludedUrls;
    }
    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

//    @Value("${accessurl}")
//    private String accessurl;

    /**
     *
     * @Description:
     * 在业务处理器处理请求之前被调用 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),
     * 再退出拦截器链, 如果返回true 执行下一个拦截器,
     * 直到所有的拦截器都执行完毕 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     *
     * @param  request
     *
     * @param  response
     *
     * @return: boolean
     *
     * @author: SongJia
     *
     * @date: 2016-6-27 下午4:17:51
     *
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String myOrigin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        return true;
        /*String[] arr = accessurl.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        if(list.contains(myOrigin)){
            response.setHeader("Access-Control-Allow-Origin", myOrigin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with, authorization,token");
            return true;
        }
        return false;*/
    }
 
    // 在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
 
    }
 
    /**
     *
     * @Description:
     * 在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,
     * 会从当前拦截器往回执行所有的拦截器的afterCompletion()
     *
     * @param request
     *
     * @param response
     *
     * @param handler
     *
     * @param ex
     *
     * @author: SongJia
     *
     * @date: 2016-6-27 下午4:27:51
     *
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}