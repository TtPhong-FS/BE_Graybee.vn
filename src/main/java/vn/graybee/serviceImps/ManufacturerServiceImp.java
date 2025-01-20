package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.repositories.business.ManufacturerRepository;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.services.business.ManufacturerService;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public void insertManufacturer(ManufacturerCreateRequest request) {
        Manufacturer manufacturer = new Manufacturer(
                request.getName().toUpperCase()
        );
        manufacturerRepository.save(manufacturer);
    }

}
