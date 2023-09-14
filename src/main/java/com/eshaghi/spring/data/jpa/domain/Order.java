package com.eshaghi.spring.data.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "\"order\"")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "order_generator")
    @SequenceGenerator(name = "order_generator", sequenceName = "seq_order", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "request_uid")
    private String requestUid;

    @Column(name = "customer_id")
    private long customerId;

    @OneToMany(mappedBy = "order", fetch = EAGER, cascade = ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Column(name = "grand_total")
    private BigDecimal grandTotal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
