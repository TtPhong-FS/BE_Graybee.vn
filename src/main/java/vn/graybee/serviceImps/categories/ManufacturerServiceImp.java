package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;
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
        manufactureValidation.checkExistByName(request.getManufacturerName());

        Manufacturer manufacturer = new Manufacturer(TextUtils.capitalize(request.getManufacturerName())
        );
        manufacturer.setStatus("ACTIVE");
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

        ManufacturerResponse manufacturerResponse = new ManufacturerResponse(
                savedManufacturer);

        return new BasicMessageResponse<>(201, "Tạo nhà sản xuất thành công!", manufacturerResponse);

    }

    @Override
    public BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST));

        if (manufacturer.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_ID_USED_IN_PRODUCT);
        }

        manufacturer.setManufacturerName(request.getManufacturerName());
        manufacturer.setStatus(request.getStatus().name());

        Manufacturer saved = manufacturerRepository.save(manufacturer);

        ManufacturerResponse response = new ManufacturerResponse(saved);

        return new BasicMessageResponse<>(200, "Cập nhật danh mục thành công!", response);
    }

    @Override
    public BasicMessageResponse<List<ManufacturerResponse>> createManufacturers(List<ManufacturerCreateRequest> request) {

        if (request == null || request.isEmpty()) {
            throw new BusinessCustomException(ConstantCategory.MANUFACTURER_NAMES, ConstantCategory.LIST_MANUFACTURER_NAME_CANNOT_BE_EMPTY);
        }

        List<Manufacturer> manufacturers = request.stream()
                .map(req -> new Manufacturer(req.getManufacturerName())).toList();

        List<Manufacturer> savedManufacturer = manufacturerRepository.saveAll(manufacturers);

        List<ManufacturerResponse> responses = savedManufacturer
                .stream()
                .map(ManufacturerResponse::new).toList();

        return new BasicMessageResponse<>(201, "Tạo nhiều nhà sản xuất thành công!", responses);
    }

    @Override
    public BasicMessageResponse<ManufacturerResponse> getById(int id) {
        ManufacturerResponse response = manufacturerRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST));

        return new BasicMessageResponse<>(200, "Tìm danh mục thành công!", response);
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
