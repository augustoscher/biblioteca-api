package br.com.objetive.biblioteca.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private String defaultTenant = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t = TenantContext.getTenantSchema();
        if (t != null) {
            return t;
        }
        return defaultTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
