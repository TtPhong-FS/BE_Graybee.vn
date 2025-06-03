package vn.graybee.modules.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SidebarDto {


    private String slug;

    private String name;

    private List<SidebarDto> children = new ArrayList<>();


}
