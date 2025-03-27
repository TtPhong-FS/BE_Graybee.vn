package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.category.ManufacturerProjection;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request);

    BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<ManufacturerResponse> getById(int id);

    BasicMessageResponse<List<ManufacturerProjection>> getAllManufacturer();

}
