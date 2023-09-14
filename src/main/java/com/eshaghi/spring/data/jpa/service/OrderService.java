package com.eshaghi.spring.data.jpa.service;


import com.eshaghi.spring.data.jpa.domain.Order;
import com.eshaghi.spring.data.jpa.repository.OrderRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private OrderRepository repository;

    public List<Order> findByCustomerId(long customerId, int pageNo, int pageSize) {
        var pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        return repository.findByCustomerId(customerId, pageable);
    }

    public int countByCustomerId(long customerId) {
        return repository.countByCustomerId(customerId);
    }

    @Transactional
    public long save(Order order) {
        return repository.findByRequestUid(order.getRequestUid())
                .map(Order::getId)
                .orElseGet(() -> repository.save(order).getId());
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
