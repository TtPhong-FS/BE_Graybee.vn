package vn.graybee.services.carousels;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.carousels.CarouselGroup;
import vn.graybee.requests.carousels.CarouselGroupRequest;

import java.util.List;

public interface CarouselGroupService {

    void checkExistsById(int id);

    BasicMessageResponse<CarouselGroup> create(CarouselGroupRequest request);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<CarouselGroup> getById(int id);

    BasicMessageResponse<CarouselGroup> update(CarouselGroupRequest request, int id);

    BasicMessageResponse<List<CarouselGroup>> getAllForDashboard();

}
