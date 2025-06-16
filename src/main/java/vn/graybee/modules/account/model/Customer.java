package vn.graybee.modules.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private Integer totalOrders;

    private double totalSpent;

    private LocalDateTime lastOrderAt;


}
