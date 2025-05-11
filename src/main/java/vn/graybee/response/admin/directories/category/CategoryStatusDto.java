package vn.graybee.response.admin.directories.category;

import vn.graybee.enums.DirectoryStatus;

public class CategoryStatusDto {

    private int id;

    private String name;

    private DirectoryStatus status;

    public CategoryStatusDto() {
    }

    public CategoryStatusDto(int id, String name, DirectoryStatus status) {
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

    public DirectoryStatus getStatus() {
        return status;
    }

    public void setStatus(DirectoryStatus status) {
        this.status = status;
    }

}
