package vn.graybee.models.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vn.graybee.models.others.BaseModel;
import vn.graybee.models.users.User;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_guest")
    private boolean isGuest;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "note", length = 200)
    private String note;

    @Column(name = "disscount_code", length = 100)
    private String discountCode;

    @Column(name = "total_money", nullable = false)
    private float totalMoney;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "issue_invoices")
    private boolean issueInvoices;

    @Column(name = "tracking_number", nullable = false, length = 50)
    private String trackingNumber;

    @Column(name = "shipping_method", length = 20)
    private String shippingMethod;

    @Column(name = "shipping_address", nullable = false, length = 200)
    private String shippingAddress;

    @Column(name = "shipping_date", nullable = false)
    private Date shippingDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private boolean isActive;

    public Order() {
    }

    public Order(LocalDateTime createdAt, LocalDateTime updatedAt, User user, boolean isGuest, String fullName, String phoneNumber, String address, String note, String discountCode, float totalMoney, LocalDateTime orderDate, boolean issueInvoices, String trackingNumber, String shippingMethod, String shippingAddress, Date shippingDate, String paymentMethod, String status, boolean isActive) {
        super(createdAt, updatedAt);
        this.user = user;
        this.isGuest = isGuest;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.note = note;
        this.discountCode = discountCode;
        this.totalMoney = totalMoney;
        this.orderDate = orderDate;
        this.issueInvoices = issueInvoices;
        this.trackingNumber = trackingNumber;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.shippingDate = shippingDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isIssueInvoices() {
        return issueInvoices;
    }

    public void setIssueInvoices(boolean issueInvoices) {
        this.issueInvoices = issueInvoices;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
