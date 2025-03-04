package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.ManufacturerProjection;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.requests.categories.ManufacturerCreateRequest;
import vn.graybee.response.categories.ManufacturerResponse;
import vn.graybee.services.categories.ManufacturerService;
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
    public BasicMessageResponse<ManufacturerResponse> insertManufacturer(ManufacturerCreateRequest request) {
        manufactureValidation.validateNameExists(request.getName());

        Manufacturer manufacturer = new Manufacturer(
                TextUtils.capitalize(request.getName())
        );
        manufacturer.setStatus("ACTIVE");
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        ManufacturerResponse manufacturerResponse = new ManufacturerResponse(
                savedManufacturer.getCreatedAt(),
                savedManufacturer.getCreatedAt(),
                savedManufacturer.getId(),
                savedManufacturer.getName(),
                savedManufacturer.getStatus(),
                savedManufacturer.getProductCount());
        return new BasicMessageResponse<>(201, "Create Manufacturer success: ", manufacturerResponse);


    }

    @Override
    public BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer() {
        List<ManufacturerProjection> manufacturerProjectionList = manufacturerRepository.findAllManufacturers();

        return new BasicMessageResponse<>(200, "List manufacturer: ", manufacturerProjectionList);

    }

    @Override
    public void deleteManufacturerById(int id) {
        manufactureValidation.checkExists(id);


        manufacturerRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(int id) {
        Manufacturer manufacturer = manufactureValidation.findToUpdateStatusDelete(id);

        manufacturerRepository.save(manufacturer);
    }

}
