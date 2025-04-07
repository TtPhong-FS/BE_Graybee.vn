package vn.graybee.models.orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.models.BaseModel;

import java.math.BigDecimal;

@Entity
@Table(name = "carts")
public class Cart extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uid")
    private Integer userUid;

    @Column(name = "session_id", length = 50)
    private String sessionId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public Cart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserUid() {
        return userUid;
    }

    public void setUserUid(Integer userUid) {
        this.userUid = userUid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
