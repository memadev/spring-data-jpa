package com.eshaghi.spring.data.jpa.domain;

import com.eshaghi.spring.data.jpa.dto.CustomerQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification implements Specification<Customer> {

    private final CustomerQuery customerQuery;

    public CustomerSpecification(CustomerQuery customerQuery) {
        this.customerQuery = customerQuery;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();

        if (StringUtils.isNotBlank(customerQuery.personNumber())) {
            predicates.add(criteriaBuilder.equal(root.get("personNumber"), customerQuery.personNumber()));
        }

        if (StringUtils.isNotBlank(customerQuery.firstName())) {
            predicates.add(criteriaBuilder.equal(root.get("firstName"), customerQuery.firstName()));
        }

        if (StringUtils.isNotBlank(customerQuery.lastName())) {
            predicates.add(criteriaBuilder.equal(root.get("lastName"), customerQuery.lastName()));
        }

        if (!predicates.isEmpty()) {
            return predicates.stream().reduce(criteriaBuilder::and).get();
        }

        return null;
    }
}
