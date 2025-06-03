package vn.graybee.modules.catalog.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.enums.CategoryStatus;
import vn.graybee.modules.catalog.model.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private List<CategoryDto> children = new ArrayList<>();

    private Long id;

    private String slug;

    private Long parentId;

    private String name;


    private CategoryStatus status;

    private int productCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
        this.productCount = category.getProductCount();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }


}
