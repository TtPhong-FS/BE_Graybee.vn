package vn.graybee.response.admin.directories.manufacturer;

public class ManufacturerDto {

    private int id;

    private String name;

    public ManufacturerDto() {
    }

    public ManufacturerDto(int id, String name) {
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

}
