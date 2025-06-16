package vn.graybee.modules.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(length = 50, nullable = false, name = "recipient_name")
    private String recipientName;

    @Column(length = 12, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 100, nullable = false)
    private String district;

    @Column(length = 50, nullable = false)
    private String commune;

    @Column(length = 150, nullable = false)
    private String street;

    @Column(name = "is_default")
    private boolean isDefault;


}
