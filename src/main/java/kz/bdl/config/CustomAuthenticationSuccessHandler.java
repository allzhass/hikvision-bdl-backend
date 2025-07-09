package kz.bdl.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // Check user role and redirect accordingly
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            
            if (role.equals("ROLE_ADMIN")) {
                // Admin can access everything, redirect to main dashboard
                response.sendRedirect("/sent-violations-view/paginated");
                return;
            } else if (role.equals("ROLE_MANAGER")) {
                // Manager has access to most features except user/role management
                response.sendRedirect("/sent-violations-view/paginated");
                return;
            } else if (role.equals("ROLE_OPERATOR")) {
                // Operator only has access to SentViolationViewController
                response.sendRedirect("/sent-violations-view/paginated");
                return;
            } else if (role.equals("ROLE_AUTO_MANAGER")) {
                // Auto manager has access to auto management and sent violations
                response.sendRedirect("/sent-violations-view/paginated");
                return;
            }
        }
        
        // Default redirect
        response.sendRedirect("/sent-violations-view/paginated");
    }
} 