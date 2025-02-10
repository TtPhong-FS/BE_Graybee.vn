package vn.graybee.services.business;

import vn.graybee.models.business.Manufacturer;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.response.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    Manufacturer insertManufacturer(ManufacturerCreateRequest request);

    List<ManufacturerResponse> getAllManufacturer();

    void deleteManufacturerById(long id);

    void updateStatusDeleteRecord(long id);

}
