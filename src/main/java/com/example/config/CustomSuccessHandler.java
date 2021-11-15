//package com.example.config;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collection;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
//public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        String redirectUrl = null;
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            System.out.println("role " + grantedAuthority.getAuthority());
//            if (grantedAuthority.getAuthority().equals("ROLE_MODERATOR")) {
//                redirectUrl = "/users/moderate";
//                clearAuthenticationAttributes(request);
//            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//                redirectUrl = "/users/list";
//                clearAuthenticationAttributes(request);
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            }else {
//                super.onAuthenticationSuccess(request,response, authentication);
//            }
//        }
//    }
//}
