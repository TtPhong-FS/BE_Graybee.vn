package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.model.Attribute;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AttributeDto {

    private Long id;

    private String name;

    private String label;

    private boolean required;

    private boolean active;

    private String inputType;

    private List<CategoryIdNameDto> categories;

    public AttributeDto(Attribute attribute, List<CategoryIdNameDto> categories) {
        this.id = attribute.getId();
        this.name = attribute.getName();
        this.label = attribute.getLabel();
        this.required = attribute.isRequired();
        this.active = attribute.isActive();
        this.inputType = attribute.getInputType();
        this.categories = categories;
    }

}
