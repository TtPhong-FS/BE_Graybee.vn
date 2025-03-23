package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.repositories.categories.ManufacturerRepository;

import java.util.Optional;

@Service
public class ManufactureValidation {

    private final ManufacturerRepository manufacturerRepository;

    public ManufactureValidation(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public int getIdByName(String manufacturerName) {
        return manufacturerRepository.getIdByName(manufacturerName)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME, ConstantCategory.MANUFACTURER_TEMPORARILY_FLAGGED));

    }

    public Manufacturer findToUpdateStatusDelete(int manufacturerId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST);
        }
        return manufacturer.get();
    }

    public void checkExists(int id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST);
        }
    }

    public void checkExistByName(String name) {
        if (manufacturerRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME, ConstantCategory.MANUFACTURER_NAME_EXISTS);
        }
    }

    public void countProductById(int id) {
        int products = manufacturerRepository.getCountProductById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST));
        if (products > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_ID_USED_IN_PRODUCT);
        }
    }

}
