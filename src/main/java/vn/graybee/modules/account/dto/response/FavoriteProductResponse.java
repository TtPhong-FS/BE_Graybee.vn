package vn.graybee.modules.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class FavoriteProductResponse {

    private long id;

    private String slug;

    private double price;

    private double finalPrice;

    private String name;

    private String thumbnail;


}
