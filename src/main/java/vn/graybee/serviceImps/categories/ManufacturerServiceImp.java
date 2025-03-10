package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.enums.CategoryStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.category.ManufacturerProjection;
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
    public BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request) {
        manufactureValidation.checkExistByName(request.getName());

        Manufacturer manufacturer = new Manufacturer(TextUtils.capitalize(request.getName())
        );
        manufacturer.setStatus(CategoryStatus.ACTIVE);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse manufacturerResponse = new ManufacturerResponse(
                savedManufacturer.getCreatedAt(),
                savedManufacturer.getCreatedAt(),
                savedManufacturer.getId(),
                savedManufacturer.getName(),
                savedManufacturer.getStatus(),
                savedManufacturer.getProductCount());

        return new BasicMessageResponse<>(201, "Tạo nhà sản xuất thành công!", manufacturerResponse);

    }

    @Override
    public BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer() {
        List<ManufacturerProjection> manufacturerProjectionList = manufacturerRepository.fetchAll();

        return new BasicMessageResponse<>(200, "List manufacturer: ", manufacturerProjectionList);

    }

    @Override
    public BasicMessageResponse<Integer> deleteById(int id) {
        manufactureValidation.countProductById(id);

        manufacturerRepository.deleteById(id);
        return new BasicMessageResponse<>(200, "Nhà sản xuất đã được xoá thành công!", id);
    }

    @Override
    public void updateStatusDeleteRecord(int id) {
        Manufacturer manufacturer = manufactureValidation.findToUpdateStatusDelete(id);

        manufacturerRepository.save(manufacturer);
    }

}
