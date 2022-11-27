package com.cdcn.apartmentonlinemarket.filter;

import com.cdcn.apartmentonlinemarket.common.Constant.CommonConstant;
import com.cdcn.apartmentonlinemarket.exception.InvalidTokenHeader;
import com.cdcn.apartmentonlinemarket.exception.model.ErrorResponse;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.debug("JwtTokenFilter is calling for uri: {} {}",
                    ((HttpServletRequest) request).getMethod(), ((HttpServletRequest) request).getRequestURI());

            String jwt = parseJwt((HttpServletRequest) request);
            String requestUri = ((HttpServletRequest) request).getRequestURI();

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.parseAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Set Authentication to security context for '{}', uri: {}",
                        authentication.getName(), requestUri);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenHeader invalidTokenHeader) {
            log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                    invalidTokenHeader.getCode(), "InvalidTokenHeader", String.join(" ",
                            ((HttpServletRequest) request).getMethod(), ((HttpServletRequest) request).getRequestURI()),
                    0, invalidTokenHeader.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message(invalidTokenHeader.getMessage()).build();
            response.setContentType("application/json");
            response.setStatus(errorResponse.getCode());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }
    }

    /**
     * parse token retrieve from header
     * slice prefix bearer token
     * */
    private String parseJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(CommonConstant.HttpAttribute.BEARER)) {
            return bearerToken.replace(CommonConstant.HttpAttribute.BEARER, "").trim();
        }
        return bearerToken;
    }
}
