package com.greatnex.semicolon_task.infrastructure.adapters.config.security;

import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();

    @Value("${keycloak.principal_attribute}")
    private String principalAttribute;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        try {
            log.info("Converting Jwt =============>>>>>>> {}", jwt);
            Collection<GrantedAuthority> authorities =
                    Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt)
                                    .stream(), extractResourceRoles(jwt).stream())
                            .collect(Collectors.toSet());
            String claimName = principalAttribute == null ? JwtClaimNames.SUB : principalAttribute;
            return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim(claimName));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AuthenticationCredentialsNotFoundException(ErrorMessages.INVALID_TOKEN);
        }
    }
        private Collection<? extends GrantedAuthority> extractResourceRoles (Jwt jwt){
            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
            Map<String, Object> resource;
            Collection<String> resourceRoles;
            List<String> clients = new ArrayList<>();
            String client = extractClientNameFromJwt(jwt, clients, resourceAccess);

            if (resourceAccess == null
                    || (resource = (Map<String, Object>) resourceAccess.get(client)) == null
                    || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
                return Collections.emptySet();
            }
            return resourceRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

        private static String extractClientNameFromJwt(Jwt jwt, List<String> clients, Map<String, Object> resourceAccess) {
            String client = "";
            log.info("extracting client name from jwt....");
            log.info("client name: {}", jwt.getClaimAsString("aud"));
            log.info("resource access : {}", resourceAccess);
            if(jwt.getClaim("aud") instanceof String) {
                log.info("Claim 'aud' is a string.....");
                clients.add((String) resourceAccess.get("aud"));
            }else if(jwt.getClaim("aud") instanceof List) {
                log.info("Claim 'aud' is a list....");
                clients.addAll(jwt.getClaim("aud"));
                log.info("Client list.... {}", clients);
            }
            if(clients.size() > 1){
                clients.remove("account");
                log.info("removing 'account' client from claim....");
            }
            if(clients.size() == 1){
                client = clients.get(0);
            }else if(clients.size() > 1){
                log.error("More than one client found");
                throw new InputMismatchException(ErrorMessages.CLIENT_MORE_THAN_ONE);
            }
            log.info("client name: =========>>>>>>>>>>>{}", client);
            return client;
        }

}
