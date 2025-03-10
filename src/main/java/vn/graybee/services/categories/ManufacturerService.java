package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.ManufacturerProjection;
import vn.graybee.requests.categories.ManufacturerCreateRequest;
import vn.graybee.response.categories.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request);

    BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer();

    BasicMessageResponse<Integer> deleteById(int id);

    void updateStatusDeleteRecord(int id);

}
