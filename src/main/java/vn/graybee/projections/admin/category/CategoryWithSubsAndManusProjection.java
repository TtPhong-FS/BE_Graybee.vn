package vn.graybee.projections.admin.category;

import java.util.List;

public interface CategoryWithSubsAndManusProjection {

    Integer getId();

    String getCategoryName();

    List<String> getManufacturers();

}
