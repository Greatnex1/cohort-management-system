package com.greatnex.semicolon_task.infrastructure.adapters.config.security;

import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.application.ports.output.PlatformUserOutputPort;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityUtils securityUtils;
   private final PlatformUserOutputPort userOutputPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            JwtAuthenticationToken principal = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            log.info("Request uri =================>>>> : " + request.getRequestURI());
            AntPathMatcher matcher = new AntPathMatcher();
            if(principal == null && Arrays.stream(WhiteList.getPatterns).anyMatch(path -> matcher.match(path, request.getRequestURI()))){
                log.info("No Authentication provided for whitelisted path {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }else {
                log.info("Authentication provided for path ============ {}", request.getRequestURI());
                assert principal != null : "Authentication is null";
                Jwt token = principal.getToken();
                log.info("token : {}", token);
                PlatformUser identity = PlatformUser.builder().email(token.getClaimAsString("email")).name(token.getClaimAsString("name"))
                        .firstName(token.getClaimAsString("given_name")).lastName(token.getClaimAsString("family_name"))
                        .username(token.getClaimAsString("preferred_username")).emailVerified(token.getClaimAsBoolean("email_verified"))
                        .id(token.getClaimAsString("sub")).build();
                extractClientIdFromJwtToken(token, identity);
                if (createUserIfNotExist(request, response, filterChain, identity, token)) return;
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(identity, principal.getCredentials(), principal.getAuthorities());
                principal.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch ( JwtException e) {
            log.error("Error while fetching user details for authentication", e);
            securityUtils.sendErrorResponse(response, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean createUserIfNotExist(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, PlatformUser identity, Jwt token) throws IOException, ServletException {
        if(!userExists(identity.getId())){
            log.info("User does not exist: {}", identity.getEmail());

            if(validatePermission(token, identity)){
                log.info("Valid permissions found. Creating user: {}", identity.getEmail());
                createUser(identity);
            }else{
                log.info("User lacks required permissions. No action taken.");
                filterChain.doFilter(request, response);
                return true;
            }
        }
        return false;
    }

    private static void extractClientIdFromJwtToken(Jwt token, PlatformUser identity) {
        if (token.getClaim("aud") instanceof String) {
            identity.setKeycloakClientId(token.getClaimAsString("aud"));
        } else if(token.getClaim("aud") instanceof List && (token.getClaimAsStringList("aud")).size() > 1){
            token.getClaimAsStringList("aud").remove("account");
            identity.setKeycloakClientId(token.getClaimAsStringList("aud").get(0));
        }else {
            identity.setKeycloakClientId(null);
        }
    }

    private boolean validatePermission(Jwt token, PlatformUser userIdentity){
        List<String> requiredPermissions = List.of("CREATE_COHORT","VIEW_COHORT","VIEW_ALL_COHORTS");

        Map<String, Object> claims = (Map<String, Object>) token.getClaimAsMap("resource_access").get(userIdentity.getKeycloakClientId());
        List<String> userPermissions = (List<String>) claims.get("roles");

        if(userPermissions == null || userPermissions.isEmpty()){
            return false;
        }

        return userPermissions.stream().anyMatch(requiredPermissions::contains);
    }

    private boolean userExists(String id){
        return userOutputPort.userExists(id);
    }

    private void createUser(PlatformUser userIdentity){
        userOutputPort.save(userIdentity);
    }

}
