package vn.graybee.modules.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.common.model.BaseModel;
import vn.graybee.modules.order.enums.OrderStatus;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "session_id", length = 20)
    private String sessionId;

    @Column(length = 200)
    private String note;

    @Column(length = 200)
    private String email;

    @Column(name = "total_amount")
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


}
