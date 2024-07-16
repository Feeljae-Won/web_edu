package com.webjjang.util.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;

/**
 * Servlet Filter implementation class ConDateUpdateFilter
 */
//@WebFilter("/ConDateUpdateFilter")
public class ConDateUpdateFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public ConDateUpdateFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// 로그인을 한 경우 최근 접속일을 수정한다.
		HttpSession session = ((HttpServletRequest) request).getSession();
		LoginVO loginVO = (LoginVO) session.getAttribute("login");
		
		String id = null;
		if(loginVO != null) {
			id = loginVO.getId();
			try {
				// 최근 접속일을 변경한다.
				Execute.execute(Init.get("/member/conUpdate.do"), id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
