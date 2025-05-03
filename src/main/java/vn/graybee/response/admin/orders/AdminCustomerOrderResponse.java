package vn.graybee.response.admin.orders;

import java.math.BigDecimal;

public class AdminCustomerOrderResponse {

    private BigDecimal totalSpent;

    private Long totalOrder;

    public AdminCustomerOrderResponse() {
    }

    public AdminCustomerOrderResponse(BigDecimal totalSpent, Long totalOrder) {
        this.totalSpent = totalSpent;
        this.totalOrder = totalOrder;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
    }

}
