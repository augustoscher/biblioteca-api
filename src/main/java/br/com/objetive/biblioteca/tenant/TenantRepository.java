package br.com.objetive.biblioteca.tenant;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TenantRepository extends PagingAndSortingRepository<Tenant, String> {

}
