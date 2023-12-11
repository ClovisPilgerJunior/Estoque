package com.janfer.estoque.configs.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

        /*
        Examples to get the logged-in user's id.

        -- Example 1:
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getUsername);

        -- Example 2:
         */

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auditor = "System";

        if (Objects.nonNull(authentication)) {
            auditor = authentication.getName();

            if (auditor.equals("anonymousUser")) {
                return Optional.of("System");
            }
        }

        return Optional.of(auditor);

  }
}