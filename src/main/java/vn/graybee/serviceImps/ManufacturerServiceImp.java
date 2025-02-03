package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.repositories.business.ManufacturerRepository;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.services.business.ManufacturerService;
import vn.graybee.validation.ManufactureValidation;

import java.util.List;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    private final ManufactureValidation manufactureValidation;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository, ManufactureValidation manufactureValidation) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufactureValidation = manufactureValidation;
    }

    @Override
    public Manufacturer insertManufacturer(ManufacturerCreateRequest request) {
        manufactureValidation.ensureManufactureNameBeforeCreate(request.getManufacturerName());
        Manufacturer manufacturer = new Manufacturer(
                request.getManufacturerName().toUpperCase(),
                request.getIsDelete()

        );
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturer() {
        return manufacturerRepository.findAll();
    }

    @Override
    public void deleteManufacturerById(long id) {
        manufactureValidation.ensureExistsById(id);

        manufactureValidation.checkProductExistsByManufacturerId(id);

        manufacturerRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(long id) {
        Manufacturer manufacturer = manufactureValidation.findToUpdateStatusDelete(id);
        if (manufacturer.getIsDelete().equals("false")) {
            manufacturer.setIsDelete("true");
        } else {
            manufacturer.setIsDelete("false");
        }
        manufacturerRepository.save(manufacturer);
    }

}
