package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeIdRequiredResponse {

    private long id;

    private boolean required;

}
