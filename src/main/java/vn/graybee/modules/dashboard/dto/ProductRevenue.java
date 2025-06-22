package vn.graybee.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ProductRevenue {

    private long productId;

    private String productName;

    private String thumbnail;

    private double totalAmount;


}
