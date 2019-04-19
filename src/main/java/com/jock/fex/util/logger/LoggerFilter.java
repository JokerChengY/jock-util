/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jock.fex.util.logger;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.StringTokenizer;

import static java.lang.System.out;

/**
 * 日志过滤器基类
 * 
 * @author think
 */
public class LoggerFilter implements Filter {

	Logger logger = Logger.getLogger(LoggerFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("init...");
		System.out.println("doFilter...");
		chain.doFilter(req, response);
		try {
			logger.debug("start logging...");
			HttpServletRequest request = (HttpServletRequest) req;
			String agent = request.getHeader("user-agent");
			System.out.println(agent);

			StringTokenizer st = new StringTokenizer(agent, ";");
			st.nextToken();
			// 得到用户的浏览器名
			String userbrowser = st.nextToken();
			System.out.println("浏览器名称：" + userbrowser);
			// 得到用户的操作系统名
			String useros = st.nextToken();
			System.out.println("操作系统名称" + useros);
			out.println("Server Name: " + request.getServerName());
			out.println("Server Port: " + request.getServerPort());
			out.println("Protocol: " + request.getProtocol());
			out.println("Remote Addr: " + request.getRemoteAddr());
			out.println("Remote Host: " + request.getRemoteHost());
			out.println("Character Encoding: " + request.getCharacterEncoding());
			out.println("Content Length: " + request.getContentLength());
			out.println("Content Type: " + request.getContentType());
			out.println("Auth Type: " + request.getAuthType());
			out.println("HTTP Method: " + request.getMethod());
			out.println("Path Info: " + request.getPathInfo());
			out.println("Path Trans: " + request.getPathTranslated());
			out.println("Query String: " + request.getQueryString());
			out.println("Remote User: " + request.getRemoteUser());
			out.println("Session Id: " + request.getRequestedSessionId());
			out.println("Request URI: " + request.getRequestURI());
			out.println("Servlet Path: " + request.getServletPath());
			out.println("Accept: " + request.getHeader("Accept"));
			out.println("Host: " + request.getHeader("Host"));
			out.println("Referer : " + request.getHeader("Referer"));
			out.println("Accept-Language : " + request.getHeader("Accept-Language"));
			out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding"));
			out.println("User-Agent : " + request.getHeader("User-Agent"));
			out.println("Connection : " + request.getHeader("Connection"));
			out.println("Cookie : " + request.getHeader("Cookie"));
			// out.println("Created : " + session.getCreationTime());
			// out.println("LastAccessed : " + session.getLastAccessedTime());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			logger.debug("do servlet...");

		}
	}

	public void destroy() {
	}

}
