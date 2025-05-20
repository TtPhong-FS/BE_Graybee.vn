package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.directories.ManufacturerCreateRequest;
import vn.graybee.requests.directories.ManufacturerUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerResponse> create(ManufacturerCreateRequest request);

    BasicMessageResponse<ManufacturerResponse> update(int id, ManufacturerUpdateRequest request);

    BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, String status);

    BasicMessageResponse<ManufacturerResponse> restoreById(int id, UserPrincipal userPrincipal);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<ManufacturerResponse> findById(int id);

    BasicMessageResponse<List<ManufacturerResponse>> findAll();

}
