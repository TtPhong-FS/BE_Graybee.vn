package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.KeyboardDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.KeyboardRepository;
import vn.graybee.requests.keyboard.KeyboardDetailCreateRequest;
import vn.graybee.services.business.KeyboardService;
import vn.graybee.services.business.ProductService;

@Service
public class KeyboardServiceImp implements KeyboardService {

    private final ProductService productService;

    private final KeyboardRepository keyboardRepository;

    public KeyboardServiceImp(ProductService productService, KeyboardRepository keyboardRepository) {
        this.productService = productService;
        this.keyboardRepository = keyboardRepository;
    }

    @Override
    @Transactional
    public void createKeyboardDetail(KeyboardDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("KEYBOARD")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_KEYBOARD_TYPE);
        }
        KeyboardDetail keyboard = new KeyboardDetail(
                product,
                request.getKeyMaterial(),
                request.getDesign(),
                request.getConnect(),
                request.getKeyCap(),
                request.getSwitchType(),
                request.getCompatible(),
                request.getFeature(),
                request.getSupport(),
                request.getLed()
        );
        keyboardRepository.save(keyboard);
    }

}
