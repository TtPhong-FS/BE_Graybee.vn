package vn.graybee.modules.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicResponse implements Serializable {

    private Long id;

    private String name;

    private String slug;

    private double price;

    private double finalPrice;

    private String thumbnail;


}
