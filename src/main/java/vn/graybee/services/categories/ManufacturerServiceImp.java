package vn.graybee.services.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerStatusDto;
import vn.graybee.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public ManufacturerResponse getManufacturerResponse(Manufacturer manufacturer) {
        return new ManufacturerResponse(manufacturer);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request) {

        if (manufacturerRepository.checkExistsByName(request.getName())) {
            throw new BusinessCustomException(ConstantManufacturer.name, ConstantManufacturer.name_exists);
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(TextUtils.capitalize(request.getName()));
        manufacturer.setStatus(DirectoryStatus.INACTIVE);

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(201, ConstantManufacturer.success_create, res);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request) {

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

        ManufacturerResponse res = getManufacturerResponse(manufacturer);


        return new BasicMessageResponse<>(200, ConstantManufacturer.success_update, res);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, DirectoryStatus status) {
        ManufacturerStatusDto manufacturer = manufacturerRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        if (manufacturer.getStatus() == DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCategory.in_removed);
        }

        if (manufacturer.getStatus() == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCategory.in_deleted);
        }

        manufacturerRepository.updateStatusById(id, status);

        UpdateStatusResponse response = new UpdateStatusResponse(id, status, LocalDateTime.now());

        return new BasicMessageResponse<>(200, ConstantGeneral.success_update_status, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> restoreById(int id, UserPrincipal userPrincipal) {

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        DirectoryStatus status = manufacturer.getStatus();

        if (userPrincipal != null && !userPrincipal.getUser().isSuperAdmin() && status == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.not_super_admin);
        }

        if (status != DirectoryStatus.DELETED && status != DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCategory.not_removed);
        }

        manufacturerRepository.updateStatusById(id, DirectoryStatus.INACTIVE);

        ManufacturerResponse res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_restore, res);
    }

    @Override
    public BasicMessageResponse<ManufacturerResponse> findById(int id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        ManufacturerResponse res = getManufacturerResponse(manufacturer);


        return new BasicMessageResponse<>(200, ConstantManufacturer.success_find_by_id, res);
    }

    @Override
    public BasicMessageResponse<List<ManufacturerResponse>> findAll() {
        List<ManufacturerResponse> responses = manufacturerRepository.getAll();

        String message = responses.isEmpty() ? ConstantGeneral.empty_list : ConstantManufacturer.success_fetch_manufacturers;

        return new BasicMessageResponse<>(200, message, responses);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        Integer productCount = manufacturerRepository.getProductCountById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.does_not_exists));

        if (productCount > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantManufacturer.products_in_use);
        }

        manufacturerRepository.deleteById(id);

        return new BasicMessageResponse<>(200, ConstantManufacturer.success_delete, id);
    }

}
