package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerProductCountResponse;
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
    public BasicMessageResponse<Manufacturer> create(ManufacturerCreateRequest request) {

        if (manufacturerRepository.checkExistsByName(request.getName())) {
            throw new BusinessCustomException(ConstantManufacturer.name, ConstantManufacturer.name_exists);
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(TextUtils.capitalize(request.getName()));
        manufacturer.setStatus(DirectoryStatus.ACTIVE);

        manufacturer = manufacturerRepository.save(manufacturer);

        return new BasicMessageResponse<>(201, ConstantManufacturer.success_create, manufacturer);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Manufacturer> update(int id, ManufacturerUpdateRequest request) {

        DirectoryStatus status = request.getStatusEnum();

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        if (!manufacturer.getName().equals(request.getName()) && manufacturerRepository.existsByNameAndNotId(request.getName(), manufacturer.getId())) {
            throw new BusinessCustomException(ConstantManufacturer.name, ConstantManufacturer.name_exists);
        }

        if (manufacturer.getProductCount() == 0) {
            manufacturer.setName(request.getName());
        }

        manufacturer.setStatus(status);
        manufacturer.setUpdatedAt(LocalDateTime.now());

        manufacturer = manufacturerRepository.save(manufacturer);

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_update, manufacturer);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, DirectoryStatus status) {
        if (!manufacturerRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists);
        }

        manufacturerRepository.updateStatusById(id, status);

        UpdateStatusResponse response = new UpdateStatusResponse(id, status, LocalDateTime.now());

        return new BasicMessageResponse<>(200, ConstantGeneral.success_update_status, response);
    }

    @Override
    public BasicMessageResponse<Manufacturer> findById(int id) {
        Manufacturer response = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_find_by_id, response);
    }

    @Override
    public BasicMessageResponse<List<Manufacturer>> findAll() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();

        String message = manufacturers.isEmpty() ? ConstantGeneral.empty_list : ConstantManufacturer.success_fetch_manufacturers;

        return new BasicMessageResponse<>(200, message, manufacturers);

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
