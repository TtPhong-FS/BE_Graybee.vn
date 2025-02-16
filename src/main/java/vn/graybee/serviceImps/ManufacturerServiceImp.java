package vn.graybee.serviceImps;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import vn.graybee.constants.manufacturers.ErrorManufacturerConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Manufacturer;
import vn.graybee.projections.ManufacturerProjection;
import vn.graybee.repositories.business.ManufacturerRepository;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.response.manufacturer.ManufacturerResponse;
import vn.graybee.services.business.ManufacturerService;
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

        try {
            Manufacturer manufacturer = new Manufacturer(
                    TextUtils.capitalize(request.getName())
            );
            manufacturer.setDeleted(false);
            Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
            ManufacturerResponse manufacturerResponse = new ManufacturerResponse(
                    savedManufacturer.getCreatedAt(),
                    savedManufacturer.getCreatedAt(),
                    savedManufacturer.getId(),
                    savedManufacturer.getName(),
                    savedManufacturer.isDeleted());
            return new BasicMessageResponse<>(201, "Create Manufacturer success: ", manufacturerResponse);

        } catch (DataIntegrityViolationException ex) {
            throw new BusinessCustomException(ErrorManufacturerConstants.NAME_ERROR, ErrorManufacturerConstants.MANUFACTURER_NAME_EXISTS);
        }
    }

    @Override
    public BasicMessageResponse<List<ManufacturerResponse>> getAllManufacturer() {
        List<ManufacturerProjection> manufacturerProjectionList = manufacturerRepository.findAllManufacturers();

        List<ManufacturerResponse> manufacturerResponses = manufacturerProjectionList.stream().map(m -> new ManufacturerResponse(m.getCreatedAt(), m.getUpdatedAt(), m.getId(), m.getName(), m.isDeleted())).toList();

        return new BasicMessageResponse<>(200, "List manufacturer: ", manufacturerResponses);

    }

    @Override
    public void deleteManufacturerById(int id) {
        manufactureValidation.checkExists(id);


        manufacturerRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(int id) {
        Manufacturer manufacturer = manufactureValidation.findToUpdateStatusDelete(id);
        manufacturer.setDeleted(!manufacturer.isDeleted());
        manufacturerRepository.save(manufacturer);
    }

}
