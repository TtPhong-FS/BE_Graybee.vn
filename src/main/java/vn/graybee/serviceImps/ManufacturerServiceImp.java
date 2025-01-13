package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.Manufacturer;
import vn.graybee.repositories.ManufacturerRepository;
import vn.graybee.requests.manufacturers.ManufacturerCreateRequest;
import vn.graybee.services.ManufacturerService;

import java.time.LocalDateTime;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public void insertManufacturer(ManufacturerCreateRequest request) {
        Manufacturer manufacturer = new Manufacturer(
                LocalDateTime.now(),
                LocalDateTime.now(),
                request.getName()
        );
        manufacturerRepository.save(manufacturer);
    }

}
