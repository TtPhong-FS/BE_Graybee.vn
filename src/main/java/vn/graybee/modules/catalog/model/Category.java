package vn.graybee.modules.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.common.model.BaseModel;
import vn.graybee.modules.catalog.enums.CategoryType;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(length = 100)
    private String slug;


    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "category_type")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(name = "is_active")
    private boolean isActive;

}
