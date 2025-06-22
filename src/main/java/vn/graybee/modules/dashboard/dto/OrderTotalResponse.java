package vn.graybee.modules.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderTotalResponse {

    private long count;

    private double totalAmount;

    public OrderTotalResponse(long count, double totalAmount) {
        this.count = count;
        this.totalAmount = totalAmount;
    }

}
