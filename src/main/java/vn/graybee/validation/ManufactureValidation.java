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

    public Manufacturer findToCreateProduct(String manufacturerName) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findByManufacturerName(manufacturerName);
        if (manufacturer.isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
        if (manufacturer.get().isDeleted()) {
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

    public void checkManufacturerNameExists(String manufacturerName) {
        if (manufacturerRepository.checkManufacturerNameExists(manufacturerName).isPresent()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.NAME_ERROR, ErrorManufacturerConstants.MANUFACTURER_NAME_EXISTS);
        }
    }

    public void checkProductExists(long manufacturerId) {
        if (manufacturerRepository.checkProductIdExists(manufacturerId).isPresent()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_ID_USED_IN_PRODUCT);
        }
    }

    public void checkExists(long id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorManufacturerConstants.GENERAL_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
        }
    }

}
