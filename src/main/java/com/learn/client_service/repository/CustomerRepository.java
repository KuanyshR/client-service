package com.learn.client_service.repository;

import com.learn.client_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Customer}; CRUD methods come from {@link JpaRepository}.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
