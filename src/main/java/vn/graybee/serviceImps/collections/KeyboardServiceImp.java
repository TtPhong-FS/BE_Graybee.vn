package vn.graybee.serviceImps.collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.KeyboardDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.collections.KeyboardRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.KeyboardDetailCreateRequest;
import vn.graybee.response.DetailDtoResponse;
import vn.graybee.services.products.ProductDetailService;

@Service
public class KeyboardServiceImp implements ProductDetailService {


    private final KeyboardRepository keyboardRepository;

    public KeyboardServiceImp(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getName().equalsIgnoreCase("keyboard")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_KEYBOARD_TYPE);
        }
        KeyboardDetailCreateRequest keyboardDto = (KeyboardDetailCreateRequest) request;
        KeyboardDetail keyboard = new KeyboardDetail(
                product,
                keyboardDto.getKeyMaterial(),
                keyboardDto.getDesign(),
                keyboardDto.getConnect(),
                keyboardDto.getKeyCap(),
                keyboardDto.getSwitchType(),
                keyboardDto.getCompatible(),
                keyboardDto.getFeature(),
                keyboardDto.getSupport(),
                keyboardDto.getLed()
        );
        keyboardRepository.save(keyboard);

    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }


    @Override
    public String getDetailType() {
        return "KeyboardDetailCreateRequest";
    }

}
