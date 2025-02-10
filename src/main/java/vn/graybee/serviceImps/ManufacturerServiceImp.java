package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.repositories.business.ManufacturerRepository;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.response.ManufacturerResponse;
import vn.graybee.services.business.ManufacturerService;
import vn.graybee.utils.TextUtils;
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
        manufactureValidation.checkManufacturerNameExists(request.getManufacturerName());
        Manufacturer manufacturer = new Manufacturer(
                TextUtils.capitalize(request.getManufacturerName())
        );
        manufacturer.setDeleted(false);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<ManufacturerResponse> getAllManufacturer() {
        return manufacturerRepository.findAllManufacturers()
                .stream()
                .map(m -> new ManufacturerResponse(m.getId(), m.getManufacturerName(), m.isDeleted(), m.getCreatedAt(), m.getUpdatedAt()))
                .toList();
    }

    @Override
    public void deleteManufacturerById(long id) {
        manufactureValidation.checkExists(id);

        manufactureValidation.checkProductExists(id);

        manufacturerRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(long id) {
        Manufacturer manufacturer = manufactureValidation.findToUpdateStatusDelete(id);
        manufacturer.setDeleted(!manufacturer.isDeleted());
        manufacturerRepository.save(manufacturer);
    }

}
