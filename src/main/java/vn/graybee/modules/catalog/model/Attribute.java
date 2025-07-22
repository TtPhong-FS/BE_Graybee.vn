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

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String label;

    @Column(name = "input_type", nullable = false, length = 30)
    private String inputType;

    @Column(name = "is_required")
    private boolean isRequired;

    @Column(name = "is_active")
    private boolean isActive;

}
