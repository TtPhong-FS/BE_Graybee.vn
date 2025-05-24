package vn.graybee.taxonomy.manufacturer.service;

import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerCreateRequest;
import vn.graybee.taxonomy.manufacturer.dto.request.ManufacturerUpdateRequest;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerDto> create(ManufacturerCreateRequest request);

    BasicMessageResponse<ManufacturerDto> update(int id, ManufacturerUpdateRequest request);

    BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status);

    BasicMessageResponse<ManufacturerDto> restoreById(int id, UserDetail userDetail);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<ManufacturerDto> findById(int id);

    BasicMessageResponse<List<ManufacturerDto>> findAll();

}
