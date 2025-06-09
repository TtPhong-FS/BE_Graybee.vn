package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.model.Attribute;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AttributeDto {

    private Long id;

    private String categoryName;

    private String name;

    private String label;

    private String unit;

    private boolean required;

    private String inputType;

    private List<String> options;

    public AttributeDto(Attribute attribute, String categoryName) {
        this.id = attribute.getId();
        this.categoryName = categoryName;
        this.name = attribute.getName();
        this.label = attribute.getLabel();
        this.unit = attribute.getUnit();
        this.required = attribute.isRequired();
        this.inputType = attribute.getInputType();
        this.options = attribute.getOptions();
    }

}
