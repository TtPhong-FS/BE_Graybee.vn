package vn.graybee.response.orders;

public class OrderIdAndTotalAmountDto {

    private long id;

    private double totalAmount;

    public OrderIdAndTotalAmountDto() {
    }

    public OrderIdAndTotalAmountDto(long id, double totalAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
