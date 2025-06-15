package vn.graybee.modules.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_descriptions")
public class ProductDescription {

    @Id
    @Column(name = "product_id")
    private Long productId;

    private String description;


}
