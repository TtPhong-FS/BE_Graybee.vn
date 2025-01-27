package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.manufacturers.ErrorManufacturerConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.repositories.business.ManufacturerRepository;

import java.util.Optional;

@Service
public class ManufactureValidation {

    private final ManufacturerRepository manufacturerRepository;

    public ManufactureValidation(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer ensureManufactureBeforeCreateProduct(long manufacturerId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
        if (manufacturer.get().getIsDelete().equals("true")) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_TEMPORARILY_FLAGGED);
        }
        return manufacturer.get();
    }

    public Manufacturer findToUpdateStatusDelete(long manufacturerId) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
        return manufacturer.get();
    }

    public void ensureManufactureNameBeforeCreate(String manufacturerName) {
        if (manufacturerRepository.ensureManufactureNameBeforeCreate(manufacturerName).isPresent()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.NAME_ERROR, ErrorManufacturerConstants.MANUFACTURER_NAME_EXISTS);
        }
    }

    public void checkProductExistsByManufacturerId(long manufacturerId) {
        if (manufacturerRepository.checkProductIdExistsByManufacturerId(manufacturerId).isPresent()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_ID_USED_IN_PRODUCT);
        }
    }

    public void ensureExistsById(long id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
    }

}
