package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ErrorManufacturerConstants;
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

    public Manufacturer findToCreateProduct(String manufacturerName) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findByName(manufacturerName);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
        if (manufacturer.get().isDeleted()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_TEMPORARILY_FLAGGED);
        }
        return manufacturer.get();
    }

    public Manufacturer findToUpdateStatusDelete(int manufacturerId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
        return manufacturer.get();
    }


    public void checkExists(int id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
    }

    public void validateNameExists(String name) {
        if (manufacturerRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.NAME_ERROR, ErrorManufacturerConstants.MANUFACTURER_NAME_EXISTS);
        }
    }

}
