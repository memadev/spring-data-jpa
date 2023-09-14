package com.eshaghi.spring.data.jpa.repository;

import com.eshaghi.spring.data.jpa.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    List<Order> findByCustomerId(long customerId, Pageable page);

    int countByCustomerId(long customerId);

    Optional<Order> findByRequestUid(String requestUid);
}
