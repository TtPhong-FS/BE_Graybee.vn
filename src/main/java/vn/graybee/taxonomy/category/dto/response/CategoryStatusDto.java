package vn.graybee.taxonomy.category.dto.response;

import vn.graybee.taxonomy.enums.TaxonomyStatus;

public class CategoryStatusDto {

    private int id;

    private String name;

    private TaxonomyStatus status;

    public CategoryStatusDto() {
    }

    public CategoryStatusDto(int id, String name, TaxonomyStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public TaxonomyStatus getStatus() {
        return status;
    }

    public void setStatus(TaxonomyStatus status) {
        this.status = status;
    }

}
