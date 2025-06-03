package vn.graybee.modules.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.common.model.BaseModel;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "attributes")
public class Attribute extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String unit;

    @Column(name = "input_type", nullable = false, length = 30)
    private String inputType;

}
