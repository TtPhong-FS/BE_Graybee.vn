package vn.graybee.services.carousels;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.requests.carousels.CarouselItemCreateRequest;
import vn.graybee.response.admin.carousels.CarouselActiveResponse;
import vn.graybee.response.admin.carousels.CarouselItemResponse;
import vn.graybee.response.admin.carousels.CarouselPosition;
import vn.graybee.response.publics.products.ProductBasicResponse;

import java.util.List;

public interface CarouselItemService {

    void checkExistsById(int id);

    BasicMessageResponse<CarouselItemResponse> create(CarouselItemCreateRequest request);

    BasicMessageResponse<CarouselItemResponse> update(CarouselItemCreateRequest request, int id);

    BasicMessageResponse<CarouselActiveResponse> updateActive(Integer id, boolean active);

    BasicMessageResponse<Integer> delete(Integer id);

    BasicMessageResponse<CarouselPosition> updatePosition(Integer id, int position);

    MessageResponse<List<CarouselItemResponse>> getCarouselForDashboard(String carouselGroupType, int page, int size, String sortBy, String order);

    MessageResponse<List<ProductBasicResponse>> getCarouselProducts(String carouselGroupType, String category, int size, String sortBy, String order);

    BasicMessageResponse<List<String>> getCategoryExistInCarouselGroup();

}
