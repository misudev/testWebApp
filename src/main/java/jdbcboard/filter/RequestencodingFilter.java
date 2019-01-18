package jdbcboard.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "RequestEncodingFilter", urlPatterns = {"/*"})
public class RequestencodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("RequestEncodingFilter init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        System.out.println("RequestEncodingFilter doFilter start!!");
        // 다음 필터를 실행
        filterChain.doFilter(servletRequest, servletResponse);  // 다음 필터 호출
        //서블릿 응답후
        System.out.println("RequestEncodingFilter doFiltered!!");
    }

    @Override
    public void destroy() {
        System.out.println("RequestEncodingFilter detroyed");
    }
}
