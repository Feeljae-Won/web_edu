package com.webjjang.util.filter;

import java.io.IOException;
import java.time.zone.ZoneRulesException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class RemoveSessionMsgFilter
 */
//@WebFilter("/RemoveSessionMsgFilter")
public class RemoveSessionMsgFilter extends HttpFilter implements Filter {
       
	// 필터 등록시 sitemesh 필터 보다 위에 적용시켜야 한다.
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		chain.doFilter(request, response); // sitemesh 이동 - html이 만들어 진다.
		
		// session의 msg를 지운다.
		((HttpServletRequest)request).getSession().removeAttribute("msg");
	}

}
