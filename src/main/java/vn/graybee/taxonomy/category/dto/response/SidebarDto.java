package vn.graybee.taxonomy.category.dto.response;

import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto;

import java.util.Collections;
import java.util.List;

public class SidebarDto {

    private int id;

    private String name;

    private List<ManufacturerBasicDto> manufacturers;

    private List<SubcategoryTagsDto> subcategories;

    public SidebarDto() {
    }

    public SidebarDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManufacturerBasicDto> getManufacturers() {
        return manufacturers != null ? manufacturers : Collections.emptyList();
    }

    public void setManufacturers(List<ManufacturerBasicDto> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<SubcategoryTagsDto> getSubcategories() {
        return subcategories != null ? subcategories : Collections.emptyList();
    }

    public void setSubcategories(List<SubcategoryTagsDto> subcategories) {
        this.subcategories = subcategories;
    }

}
