package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.HandheldDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.HandheldRepository;
import vn.graybee.requests.handheld.HandheldDetailCreateRequest;
import vn.graybee.services.business.HandheldService;
import vn.graybee.services.business.ProductService;

@Service
public class HandheldServiceImp implements HandheldService {

    private final HandheldRepository handheldRepository;

    private final ProductService productService;

    public HandheldServiceImp(HandheldRepository handheldRepository, ProductService productService) {
        this.handheldRepository = handheldRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void createHandheldDetail(HandheldDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("HANDHELD")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HANDHELD_TYPE);
        }
        HandheldDetail handheld = new HandheldDetail(
                product,
                request.getConnectMode(),
                request.getNumberOfNodes(),
                request.getSupport(),
                request.getUsageTime(),
                request.getBattery(),
                request.getCharging(),
                request.getLed()
        );
        handheldRepository.save(handheld);
    }

}
