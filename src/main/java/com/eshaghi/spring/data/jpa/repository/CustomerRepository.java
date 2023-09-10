package com.eshaghi.spring.data.jpa.repository;

import com.eshaghi.spring.data.jpa.domain.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByPersonNumber(String personNumber);

    @Modifying
    void deleteByPersonNumber(String personNumber);
}
