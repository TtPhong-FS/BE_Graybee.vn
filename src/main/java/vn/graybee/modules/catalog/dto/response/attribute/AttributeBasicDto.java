package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeBasicDto {

    private Long id;

    private String name;

    private String label;

    private boolean required;

    private String inputType;

    private List<String> options;

}
