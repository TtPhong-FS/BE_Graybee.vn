package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerProductCountResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
import vn.graybee.services.categories.ManufacturerService;
import vn.graybee.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request) {

        if (manufacturerRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantManufacturer.name, ConstantManufacturer.name_exists);
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(TextUtils.capitalize(request.getName()));
        manufacturer.setStatus("ACTIVE");

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse manufacturerResponse = new ManufacturerResponse(
                manufacturer);

        return new BasicMessageResponse<>(201, ConstantManufacturer.success_create, manufacturerResponse);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request) {

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        if (!manufacturer.getName().equals(request.getName()) && manufacturerRepository.existsByNameAndNotId(request.getName(), manufacturer.getId())) {
            throw new BusinessCustomException(ConstantManufacturer.name, ConstantManufacturer.name_exists);
        }

        if (manufacturer.getProductCount() == 0) {
            manufacturer.setName(request.getName());
        }

        manufacturer.setStatus(request.getStatus().name());
        manufacturer.setUpdatedAt(LocalDateTime.now());

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse response = new ManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_update, response);
    }

    @Override
    public BasicMessageResponse<ManufacturerResponse> getById(int id) {
        ManufacturerResponse response = manufacturerRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_find_by_id, response);
    }

    @Override
    public BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer() {
        List<ManufacturerProjection> manufacturers = manufacturerRepository.fetchAll();

        if (manufacturers.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, manufacturers);
        }

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_fetch_manufacturers, manufacturers);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        ManufacturerProductCountResponse manufacturer = manufacturerRepository.checkExistsByIdAndGetProductCount(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        if (manufacturer.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.products_in_use);
        }

        manufacturerRepository.deleteById(id);

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_delete, manufacturer.getId());
    }

}
