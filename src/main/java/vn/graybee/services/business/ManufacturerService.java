package vn.graybee.services.business;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.manufacturer.ManufacturerCreateRequest;
import vn.graybee.response.manufacturer.ManufacturerResponse;

import java.util.List;

public interface ManufacturerService {

    BasicMessageResponse<ManufacturerResponse> insertManufacturer(ManufacturerCreateRequest request);

    BasicMessageResponse<List<ManufacturerResponse>> getAllManufacturer();

    void deleteManufacturerById(int id);

    void updateStatusDeleteRecord(int id);

}
