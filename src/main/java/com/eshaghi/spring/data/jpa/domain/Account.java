package com.eshaghi.spring.data.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "account")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "seq_account", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "account_number")
    private String accountNumber;
}
