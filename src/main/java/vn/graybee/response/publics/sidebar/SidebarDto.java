package vn.graybee.response.publics.sidebar;

import vn.graybee.response.admin.directories.manufacturer.ManufacturerDto;

import java.util.Collections;
import java.util.List;

public class SidebarDto {

    private int id;

    private String name;

    private List<ManufacturerDto> manufacturers;

    private List<SubcategoryDto> subcategories;

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

    public List<ManufacturerDto> getManufacturers() {
        return manufacturers != null ? manufacturers : Collections.emptyList();
    }

    public void setManufacturers(List<ManufacturerDto> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<SubcategoryDto> getSubcategories() {
        return subcategories != null ? subcategories : Collections.emptyList();
    }

    public void setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }

}
