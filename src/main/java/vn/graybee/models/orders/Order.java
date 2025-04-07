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
@Table(name = "orders")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uid")
    private Integer userUid;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "disscount_id")
    private Integer discountId;

    @Column(name = "is_guest")
    private boolean isGuest;

    @Column(length = 200)
    private String note;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "issue_invoices")
    private boolean issueInvoices;

    private String status;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserUid() {
        return userUid;
    }

    public void setUserUid(Integer userUid) {
        this.userUid = userUid;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isIssueInvoices() {
        return issueInvoices;
    }

    public void setIssueInvoices(boolean issueInvoices) {
        this.issueInvoices = issueInvoices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
