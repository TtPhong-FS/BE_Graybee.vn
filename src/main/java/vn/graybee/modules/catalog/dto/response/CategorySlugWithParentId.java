package vn.graybee.modules.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySlugWithParentId {

    private Long id;

    private String slug;

    private String name;

    private Long parentId;

}
