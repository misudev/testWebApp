package jdbcboard.filter;

import jdbcboard.dto.User;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName="SecurityFilter", urlPatterns = {"/delete", "/write"})
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("요청이 올때");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User logininfo = (User) session.getAttribute("logininfo");
        if (logininfo == null) {
            response.sendRedirect("/login" + "?redirect=" + (request.getRequestURI()).substring(1));

            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("서블릿이 실행된 이후");
    }

    @Override
    public void destroy() {

    }
}