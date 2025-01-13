package vn.graybee.services;

import vn.graybee.requests.categories.CategoryCreateRequest;

public interface CategoryService {

    void insertCategory(CategoryCreateRequest request);

}
