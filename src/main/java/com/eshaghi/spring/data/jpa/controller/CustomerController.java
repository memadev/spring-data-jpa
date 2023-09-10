package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.dto.CustomerDocument;
import com.eshaghi.spring.data.jpa.dto.CustomerDto;
import com.eshaghi.spring.data.jpa.dto.CustomerQuery;
import com.eshaghi.spring.data.jpa.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.eshaghi.spring.data.jpa.config.AppProfile.INTEGRATION_TEST;
import static com.eshaghi.spring.data.jpa.config.AppProfile.NORMAL;
import static com.eshaghi.spring.data.jpa.converter.CustomerConverter.convert;
import static com.eshaghi.spring.data.jpa.converter.CustomerConverter.convertForAdd;
import static com.eshaghi.spring.data.jpa.converter.CustomerConverter.convertForUpdate;
import static com.eshaghi.spring.data.jpa.converter.CustomerConverter.convertToDtoList;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@Profile({NORMAL, INTEGRATION_TEST})
@AllArgsConstructor
class CustomerController {

    private CustomerService service;

    @GetMapping
    public CustomerDocument findAll() {
        return new CustomerDocument(convertToDtoList(service.findAll()));
    }

    @PostMapping
    public CustomerDocument find(CustomerQuery query) {
        return new CustomerDocument(convertToDtoList(service.findAll(query)));
    }

    @GetMapping("/{personNumber}")
    public CustomerDto findByPersonNumber(@PathVariable String personNumber) {
        return convert(service.findByPersonNumber(personNumber));
    }

    @PostMapping
    public void add(@RequestBody CustomerDto dto) {
        service.save(convertForAdd(dto));
    }

    @PutMapping
    public void update(@RequestBody CustomerDto dto) {
        service.save(convertForUpdate(dto));
    }

    @DeleteMapping("/{personNumber}")
    public void delete(@PathVariable String personNumber) {
        service.deleteByPersonNumber(personNumber);
    }
}
