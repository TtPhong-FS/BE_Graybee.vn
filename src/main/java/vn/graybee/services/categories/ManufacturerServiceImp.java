package vn.graybee.services.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import vn.graybee.utils.MessageSourceUtil;
import vn.graybee.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManufacturerServiceImp implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    private final MessageSourceUtil messageSourceUtil;

    public ManufacturerServiceImp(ManufacturerRepository manufacturerRepository, MessageSourceUtil messageSourceUtil) {
        this.manufacturerRepository = manufacturerRepository;
        this.messageSourceUtil = messageSourceUtil;
    }

    public ManufacturerResponse getManufacturerResponse(Manufacturer manufacturer) {
        return new ManufacturerResponse(manufacturer);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request) {

        if (manufacturerRepository.checkExistsByName(request.getName())) {
            throw new BusinessCustomException(ConstantManufacturer.name, messageSourceUtil.get("manufacturer.name_exists", new Object[]{request.getName()}));
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(TextUtils.capitalize(request.getName()));
        manufacturer.setStatus(DirectoryStatus.PENDING);

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("manufacturer.success_create", new Object[]{manufacturer.getName()}), res);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request) {

        DirectoryStatus status = request.getStatusEnum();

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        if (!manufacturer.getName().equals(request.getName()) && manufacturerRepository.existsByNameAndNotId(request.getName(), manufacturer.getId())) {
            throw new BusinessCustomException(ConstantManufacturer.name, messageSourceUtil.get("manufacturer.name_exists", new Object[]{request.getName()}));
        }

        if (manufacturer.getProductCount() == 0) {
            manufacturer.setName(request.getName());
        }

        manufacturer.setStatus(status);
        manufacturer.setUpdatedAt(LocalDateTime.now());

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_update", new Object[]{manufacturer.getName()}), res);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, String status) {

        DirectoryStatus newStatus = DirectoryStatus.getStatus(status, messageSourceUtil);

        ManufacturerStatusDto manufacturer = manufacturerRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        if (manufacturer.getStatus() == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.status_not_changed", new Object[]{manufacturer.getName()}), null);
        }

        if (manufacturer.getStatus() == DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.removed", new Object[]{manufacturer.getName()}));
        }

        if (manufacturer.getStatus() == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.deleted", new Object[]{manufacturer.getName()}));
        }

        if (newStatus == DirectoryStatus.PENDING
                && (manufacturer.getStatus() == DirectoryStatus.ACTIVE || manufacturer.getStatus() == DirectoryStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.status.cannot_return"));
        }

        manufacturerRepository.updateStatusById(id, newStatus);

        UpdateStatusResponse response = new UpdateStatusResponse(id, newStatus, LocalDateTime.now());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_update", new Object[]{manufacturer.getName()}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerResponse> restoreById(int id, UserPrincipal userPrincipal) {

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        DirectoryStatus currentStatus = manufacturer.getStatus();

        if (userPrincipal != null && !userPrincipal.getUser().isSuperAdmin() && currentStatus == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.permission.super_admin_required"));
        }

        if (currentStatus != DirectoryStatus.DELETED && currentStatus != DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.not_removed", new Object[]{manufacturer.getName()}));
        }

        manufacturerRepository.updateStatusById(id, DirectoryStatus.INACTIVE);

        ManufacturerResponse res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_restore", new Object[]{manufacturer.getName()}), res);
    }

    @Override
    public BasicMessageResponse<ManufacturerResponse> findById(int id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error_does_not_exists")));

        ManufacturerResponse res = getManufacturerResponse(manufacturer);


        return new BasicMessageResponse<>(200, ConstantManufacturer.success_find_by_id, res);
    }

    @Override
    public BasicMessageResponse<List<ManufacturerResponse>> findAll() {
        List<ManufacturerResponse> responses = manufacturerRepository.getAll();

        String message = responses.isEmpty() ? messageSourceUtil.get("manufacturer.empty_list") : messageSourceUtil.get("manufacturer.success_fetch_manufacturers");

        return new BasicMessageResponse<>(200, message, responses);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        Integer productCount = manufacturerRepository.getProductCountById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        if (productCount > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.in_use"));
        }

        manufacturerRepository.deleteById(id);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_delete"), id);
    }

}
