package vn.graybee.services.business;

import vn.graybee.models.business.Manufacturer;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;

public interface ManufacturerService {

    Manufacturer insertManufacturer(ManufacturerCreateRequest request);

    void deleteManufacturerById(long id);

    void updateStatusDeleteRecord(long id);

}
