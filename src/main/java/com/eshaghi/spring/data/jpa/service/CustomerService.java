package com.eshaghi.spring.data.jpa.service;


import com.eshaghi.spring.data.jpa.domain.Customer;
import com.eshaghi.spring.data.jpa.domain.CustomerSpecification;
import com.eshaghi.spring.data.jpa.dto.CustomerQuery;
import com.eshaghi.spring.data.jpa.exception.NotFoundException;
import com.eshaghi.spring.data.jpa.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private CustomerRepository repository;

    public List<Customer> findAll(CustomerQuery query) {
        return repository.findAll(new CustomerSpecification(query));
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findByPersonNumber(String personNumber) {
        return repository.findByPersonNumber(personNumber)
                .orElseThrow(() -> new NotFoundException(String.format("Could not find customer, personNumber: %s", personNumber)));
    }

    @Transactional
    public void save(Customer entity) {
        Optional<Customer> optionalCustomer = repository.findByPersonNumber(entity.getPersonNumber());
        if (optionalCustomer.isEmpty()) {
            repository.save(entity);
        }
    }

    @Transactional
    public void deleteByPersonNumber(String personNumber) {
        repository.deleteByPersonNumber(personNumber);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
