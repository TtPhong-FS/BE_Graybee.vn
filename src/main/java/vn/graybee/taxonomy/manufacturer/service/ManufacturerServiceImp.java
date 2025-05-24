package vn.graybee.taxonomy.manufacturer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantManufacturer;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerCreateRequest;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerUpdateRequest;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerDto;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerStatusDto;
import vn.graybee.taxonomy.manufacturer.model.Manufacturer;
import vn.graybee.taxonomy.manufacturer.repository.ManufacturerRepository;

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

    public ManufacturerDto getManufacturerResponse(Manufacturer manufacturer) {
        return new ManufacturerDto(manufacturer);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerDto> create(ManufacturerCreateRequest request) {

        if (manufacturerRepository.checkExistsByName(request.getName())) {
            throw new BusinessCustomException(ConstantManufacturer.name, messageSourceUtil.get("manufacturer.name_exists", new Object[]{request.getName()}));
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(TextUtils.capitalize(request.getName()));
        manufacturer.setStatus(TaxonomyStatus.PENDING);

        manufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerDto res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("manufacturer.success_create", new Object[]{manufacturer.getName()}), res);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerDto> update(int id, ManufacturerUpdateRequest request) {

        TaxonomyStatus status = TaxonomyStatus.getStatus(request.getStatus(), messageSourceUtil);

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

        ManufacturerDto res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_update", new Object[]{manufacturer.getName()}), res);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status) {

        TaxonomyStatus newStatus = TaxonomyStatus.getStatus(status, messageSourceUtil);

        ManufacturerStatusDto manufacturer = manufacturerRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        if (manufacturer.getStatus() == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.status_not_changed", new Object[]{manufacturer.getName()}), null);
        }

        if (manufacturer.getStatus() == TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.removed", new Object[]{manufacturer.getName()}));
        }

        if (manufacturer.getStatus() == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.deleted", new Object[]{manufacturer.getName()}));
        }

        if (newStatus == TaxonomyStatus.PENDING
                && (manufacturer.getStatus() == TaxonomyStatus.ACTIVE || manufacturer.getStatus() == TaxonomyStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.status.cannot_return"));
        }

        manufacturerRepository.updateStatusById(id, newStatus);

        UpdateStatusDto response = new UpdateStatusDto(id, newStatus, LocalDateTime.now());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_update", new Object[]{manufacturer.getName()}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ManufacturerDto> restoreById(int id, UserDetail userDetail) {

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.does_not_exists")));

        TaxonomyStatus currentStatus = manufacturer.getStatus();

        if (userDetail != null && !userDetail.getUser().isSuperAdmin() && currentStatus == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.permission.super_admin_required"));
        }

        if (currentStatus != TaxonomyStatus.DELETED && currentStatus != TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error.not_removed", new Object[]{manufacturer.getName()}));
        }

        manufacturerRepository.updateStatusById(id, TaxonomyStatus.INACTIVE);

        ManufacturerDto res = getManufacturerResponse(manufacturer);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("manufacturer.success_restore", new Object[]{manufacturer.getName()}), res);
    }

    @Override
    public BasicMessageResponse<ManufacturerDto> findById(int id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("manufacturer.error_does_not_exists")));

        ManufacturerDto res = getManufacturerResponse(manufacturer);


        return new BasicMessageResponse<>(200, ConstantManufacturer.success_find_by_id, res);
    }

    @Override
    public BasicMessageResponse<List<ManufacturerDto>> findAll() {
        List<ManufacturerDto> responses = manufacturerRepository.getAll();

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
