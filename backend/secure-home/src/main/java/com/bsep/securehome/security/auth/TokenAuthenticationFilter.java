package com.bsep.securehome.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bsep.securehome.service.contract.IInvalidTokenService;
import com.bsep.securehome.utils.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenUtils tokenUtils;

    private UserDetailsService userDetailsService;

    private IInvalidTokenService invalidTokenService;

    protected final Log LOGGER = LogFactory.getLog(getClass());

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService,
                                     IInvalidTokenService invalidTokenService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
        this.invalidTokenService = invalidTokenService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String username;
        String authToken = tokenUtils.getToken(request);
//        String secureContent = tokenUtils.getSecureContentFromCookie(request);
        String secureContent = "";
        try {
            if (authToken != null) {
                invalidTokenService.findByToken(authToken);

                username = tokenUtils.getUsernameFromToken(authToken);
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (tokenUtils.validateToken(authToken, userDetails, secureContent)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Token expired!");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
