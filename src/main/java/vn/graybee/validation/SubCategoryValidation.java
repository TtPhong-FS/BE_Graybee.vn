package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.categories.SubCategoryRepository;

@Service
public class SubCategoryValidation {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryValidation(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public void checkExistsByName(String name) {
        if (subCategoryRepository.checkExistsByName(name).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.SUBCATEGORY_NAME, ConstantCategory.SUBCATEGORY_NAME_EXISTS);
        }
    }

    public int checkExistsById(int id) {
        return subCategoryRepository.checkExistsById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST));
    }

}
