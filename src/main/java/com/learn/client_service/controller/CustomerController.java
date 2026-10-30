package com.learn.client_service.controller;

import com.learn.client_service.dto.CustomerRequest;
import com.learn.client_service.dto.CustomerResponse;
import com.learn.client_service.entity.Customer;
import com.learn.client_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST endpoints for Create, Read, Update and Delete operations on {@link Customer}.
 * Read access is for USER and ADMIN, changes are for ADMIN only (see SecurityConfig).
 */
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository repository;

    /** Returns all customers. */
    @GetMapping
    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(CustomerResponse::from).toList();
    }

    /** Returns one customer, or 404 if it does not exist. */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(c -> ResponseEntity.ok(CustomerResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    /** Creates a customer and returns it with 201 and a Location header. */
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
        Customer saved = repository.save(Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build());
        return ResponseEntity.created(URI.create("/customers/" + saved.getId()))
                .body(CustomerResponse.from(saved));
    }

    /** Updates name and email of an existing customer, or returns 404. */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id,
                                                   @RequestBody CustomerRequest request) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setEmail(request.getEmail());
                    return ResponseEntity.ok(CustomerResponse.from(repository.save(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** Deletes a customer: 204 on success, 404 if it does not exist. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
