package vn.graybee.serviceImps.carousels;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCarousel;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.carousels.CarouselGroup;
import vn.graybee.repositories.carousels.CarouselGroupRepository;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.requests.carousels.CarouselGroupRequest;
import vn.graybee.services.carousels.CarouselGroupService;

import java.util.List;

@Service
public class CarouselGroupServiceImpl implements CarouselGroupService {

    private final CarouselGroupRepository carouselGroupRepository;

    private final CategoryRepository categoryRepository;

    public CarouselGroupServiceImpl(CarouselGroupRepository carouselGroupRepository, CategoryRepository categoryRepository) {
        this.carouselGroupRepository = carouselGroupRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void checkExistsById(int id) {
        if (!carouselGroupRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCarousel.does_not_exists);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselGroup> create(CarouselGroupRequest request) {

        if (carouselGroupRepository.checkExistsByName(request.getName())) {
            throw new BusinessCustomException(ConstantCarousel.name, ConstantCarousel.name_exists);
        }

        String categoryName = categoryRepository.getNameById(request.getCategoryId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        CarouselGroup carouselGroup = new CarouselGroup();

        carouselGroup.setCategoryName(categoryName);
        carouselGroup.setType(request.getType());
        carouselGroup.setName(request.getName());
        carouselGroup.setActive(request.isActive());

        carouselGroup = carouselGroupRepository.save(carouselGroup);


        return new BasicMessageResponse<>(201, ConstantCarousel.success_create, carouselGroup);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {
        checkExistsById(id);

        carouselGroupRepository.deleteById(id);
        return new BasicMessageResponse<>(200, null, id);
    }

    @Override
    public BasicMessageResponse<CarouselGroup> getById(int id) {

        CarouselGroup carouselGroup = carouselGroupRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCarousel.does_not_exists));
        
        return new BasicMessageResponse<>(200, null, carouselGroup);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CarouselGroup> update(CarouselGroupRequest request, int id) {

        CarouselGroup carouselGroup = carouselGroupRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCarousel.does_not_exists));

        if (!carouselGroup.getName().equals(request.getName()) && carouselGroupRepository.checkExistsByNameAndNotId(request.getName(), carouselGroup.getId())) {
            throw new BusinessCustomException(ConstantCarousel.name, ConstantCarousel.name_exists);
        }

        String categoryName = categoryRepository.getNameById(request.getCategoryId())
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.categoryId, ConstantCategory.does_not_exists));

        carouselGroup.setType(request.getType());
        carouselGroup.setName(request.getName());
        carouselGroup.setCategoryName(categoryName);
        carouselGroup.setActive(request.isActive());

        carouselGroup = carouselGroupRepository.save(carouselGroup);

        return new BasicMessageResponse<>(201, ConstantCarousel.success_create, carouselGroup);
    }

    @Override
    public BasicMessageResponse<List<CarouselGroup>> getAllForDashboard() {

        List<CarouselGroup> responses = carouselGroupRepository.findAll();

        String message = !responses.isEmpty() ? ConstantCarousel.success_fetch_all : ConstantGeneral.empty_list;

        return new BasicMessageResponse<>(201, message, responses);
    }

}
