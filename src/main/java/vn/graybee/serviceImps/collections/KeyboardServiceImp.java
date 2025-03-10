package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.KeyboardDetail;
import vn.graybee.projections.collections.KeyboardDetailProjection;
import vn.graybee.repositories.collections.KeyboardRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.KeyboardDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class KeyboardServiceImp implements ProductDetailService {

    private final EntityManager entityManager;


    private final KeyboardRepository keyboardRepository;

    public KeyboardServiceImp(EntityManager entityManager, KeyboardRepository keyboardRepository) {
        this.entityManager = entityManager;
        this.keyboardRepository = keyboardRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {
        if (!detailType.equalsIgnoreCase("keyboard")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_KEYBOARD_TYPE);
        }
        KeyboardDetailCreateRequest keyboardDto = (KeyboardDetailCreateRequest) request;
        KeyboardDetail keyboard = new KeyboardDetail(
                productId,
                keyboardDto.getDesign(),
                keyboardDto.getConnect(),
                keyboardDto.getKeyCap(),
                keyboardDto.getSwitchType(),
                keyboardDto.getCompatible(),
                keyboardDto.getFeature(),
                keyboardDto.getSupport(),
                keyboardDto.getLed()
        );
        entityManager.persist(keyboard);
    }

    @Override
    public String getDetailType() {
        return "KeyboardDetailCreateRequest";
    }


    public BasicMessageResponse<List<KeyboardDetailProjection>> fetchAll() {
        List<KeyboardDetailProjection> keyboards = keyboardRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách Keyboard: ", keyboards);
    }

}
