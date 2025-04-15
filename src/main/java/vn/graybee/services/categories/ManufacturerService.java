package vn.graybee.services.categories;

import vn.graybee.enums.DirectoryStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<Manufacturer> create(ManufacturerCreateRequest request);

    BasicMessageResponse<Manufacturer> update(int id, ManufacturerUpdateRequest request);

    BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, DirectoryStatus status);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<Manufacturer> findById(int id);

    BasicMessageResponse<List<Manufacturer>> findAll();

}
