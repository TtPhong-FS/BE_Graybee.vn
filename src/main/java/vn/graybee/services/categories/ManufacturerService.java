package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.ManufacturerProjection;
import vn.graybee.requests.categories.ManufacturerCreateRequest;
import vn.graybee.response.categories.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerResponse> insertManufacturer(ManufacturerCreateRequest request);

    BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer();

    void deleteManufacturerById(int id);

    void updateStatusDeleteRecord(int id);

}
