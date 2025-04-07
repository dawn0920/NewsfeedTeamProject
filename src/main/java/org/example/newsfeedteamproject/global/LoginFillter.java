package org.example.newsfeedteamproject.global;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j

public class LoginFillter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/user", "/post", "/comment"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            String requestURI = httpRequest.getRequestURI();

            log.info("로그인 필터 로직 실행");

            if (!isWhiteList(requestURI)) {
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute("sessionKey값") == null) {
                    throw new RuntimeException("로그인 해주세요.");
                }
            }

            filterChain.doFilter(httpRequest, httpResponse);
        }

        private boolean isWhiteList(String requestURI) {
            for(String white : WHITE_LIST) {
                if(requestURI.startsWith(white)) {
                    return true;
                }
            }
            return false;
        }
    }
