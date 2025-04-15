package vn.graybee.serviceImps.carousels;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCarousel;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.messages.other.PaginationInfo;
import vn.graybee.messages.other.SortInfo;
import vn.graybee.models.carousels.CarouselItem;
import vn.graybee.repositories.carousels.CarouselGroupRepository;
import vn.graybee.repositories.carousels.CarouselItemRepository;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.carousels.CarouselItemCreateRequest;
import vn.graybee.response.admin.carousels.CarouselActiveResponse;
import vn.graybee.response.admin.carousels.CarouselItemResponse;
import vn.graybee.response.admin.carousels.CarouselPosition;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.services.carousels.CarouselItemService;

import java.util.Collections;
import java.util.List;

@Service
public class CarouselItemServiceImpl implements CarouselItemService {

    private final CategoryRepository categoryRepository;

    private final CarouselGroupRepository carouselGroupRepository;

    private final ProductRepository productRepository;

    private final CarouselItemRepository carouselItemRepository;

    public CarouselItemServiceImpl(CategoryRepository categoryRepository, CarouselGroupRepository carouselGroupRepository, ProductRepository productRepository, CarouselItemRepository carouselItemRepository) {
        this.categoryRepository = categoryRepository;
        this.carouselGroupRepository = carouselGroupRepository;
        this.productRepository = productRepository;
        this.carouselItemRepository = carouselItemRepository;
    }

    public CarouselItemResponse getCarouselItemResponse(CarouselItem carouselItem, ProductBasicResponse product, String carouselGroupType) {
        CarouselItemResponse res = new CarouselItemResponse();
        res.setId(carouselItem.getId());
        res.setCarouselGroupType(carouselGroupType);
        res.setActive(carouselItem.isActive());
        res.setPosition(carouselItem.getPosition());

        res.setProductId(product.getId());
        res.setProductName(product.getName());
        res.setPrice(product.getPrice());
        res.setFinalPrice(product.getFinalPrice());
        res.setThumbnail(product.getThumbnail());

        return res;
    }

    @Override
    public void checkExistsById(int id) {
        if (!carouselItemRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCarousel.does_not_exists);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselItemResponse> create(CarouselItemCreateRequest request) {

        String carouselGroupType = carouselGroupRepository.getTypeById(request.getCarouselGroupId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCarousel.type, ConstantCarousel.does_not_exists));

        ProductBasicResponse product = productRepository.findBasicById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        CarouselItem carouselItem = new CarouselItem();
        carouselItem.setCarouselGroupId(request.getCarouselGroupId());
        carouselItem.setProductId(product.getId());
        carouselItem.setActive(request.isActive());
        carouselItem.setPosition(request.getPosition());

        carouselItem = carouselItemRepository.save(carouselItem);

        CarouselItemResponse response = getCarouselItemResponse(carouselItem, product, carouselGroupType);

        return new BasicMessageResponse<>(201, ConstantCarousel.success_create, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselItemResponse> update(CarouselItemCreateRequest request, int id) {

        ProductBasicResponse product = productRepository.findBasicById(request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        String carouselGroupType = carouselGroupRepository.getTypeById(request.getCarouselGroupId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCarousel.type, ConstantCarousel.does_not_exists));


        CarouselItem carouselItem = carouselItemRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCarousel.does_not_exists));

        carouselItem.setCarouselGroupId(request.getCarouselGroupId());
        carouselItem.setProductId(product.getId());
        carouselItem.setActive(request.isActive());
        carouselItem.setPosition(request.getPosition());

        carouselItem = carouselItemRepository.save(carouselItem);

        CarouselItemResponse response = getCarouselItemResponse(carouselItem, product, carouselGroupType);

        return new BasicMessageResponse<>(201, ConstantCarousel.success_create, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselActiveResponse> updateActive(Integer id, boolean active) {
        checkExistsById(id);

        carouselItemRepository.updateActiveById(id, active);

        CarouselActiveResponse response = new CarouselActiveResponse(id, active);

        return new BasicMessageResponse<>(200, null, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(Integer id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselPosition> updatePosition(Integer id, int position) {
        checkExistsById(id);

        carouselItemRepository.updatePositionById(id, position);

        CarouselPosition response = new CarouselPosition(id, position);

        return new BasicMessageResponse<>(200, null, response);
    }

    @Override
    public MessageResponse<List<CarouselItemResponse>> getCarouselForDashboard(String carouselGroupType, int page, int size, String sortBy, String order) {
        carouselGroupRepository.checkExistsByType(carouselGroupType);

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CarouselItemResponse> carouselPage = carouselItemRepository.getCarouselForDashboard(carouselGroupType.toLowerCase(), pageable);

        PaginationInfo paginationInfo = new PaginationInfo(
                carouselPage.getNumber(),
                carouselPage.getTotalPages(),
                carouselPage.getTotalElements(),
                carouselPage.getSize()
        );

        SortInfo sortInfo = new SortInfo(sortBy, order);

        String message = !carouselPage.getContent().isEmpty() ? ConstantCarousel.success_fetch_all : ConstantGeneral.empty_list;

        return new MessageResponse<>(200, message, carouselPage.getContent(), paginationInfo, sortInfo);
    }

    @Override
    public MessageResponse<List<ProductBasicResponse>> getCarouselProducts(String carouselGroupType, String category, int size, String sortBy, String order) {

        if (!carouselGroupRepository.checkExistsByType(carouselGroupType)) {
            return new MessageResponse<>(200, "Loại carousel này hiện chưa có dữ liệu. Vui lòng tìm loại carousel khác", Collections.emptyList(), null, null);
        }

        if (!carouselGroupRepository.checkExistsByCategoryName(category)) {
            return new MessageResponse<>(200, "Danh mục này hiện chưa tạo Carousel. Vui lòng thử danh mục khác", Collections.emptyList(), null, null);
        }

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(0, size, sort);

        Page<ProductBasicResponse> productPage = carouselItemRepository.getCarouselProducts(carouselGroupType.toLowerCase(), category.toLowerCase(), pageable);

        PaginationInfo paginationInfo = new PaginationInfo(
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getTotalElements(),
                productPage.getSize()
        );

        SortInfo sortInfo = new SortInfo(sortBy, order);

        String message = productPage.getContent().isEmpty()
                ? ConstantGeneral.empty_list
                : ConstantProduct.success_fetch_products;

        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
    }

    @Override
    public BasicMessageResponse<List<String>> getCategoryExistInCarouselGroup() {

        List<String> categories = carouselGroupRepository.fetchCategoriesExists();

        return new BasicMessageResponse<>(200, null, categories);
    }

}
