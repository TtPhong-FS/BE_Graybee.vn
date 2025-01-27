package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.MouseDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.MouseRepository;
import vn.graybee.requests.mouse.MouseDetailCreateRequest;
import vn.graybee.services.business.MouseService;
import vn.graybee.services.business.ProductService;

@Service
public class MouseServiceImp implements MouseService {

    private final ProductService productService;

    private final MouseRepository mouseRepository;

    public MouseServiceImp(ProductService productService, MouseRepository mouseRepository) {
        this.productService = productService;
        this.mouseRepository = mouseRepository;
    }

    @Override
    @Transactional
    public void createMouseDetail(MouseDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("MOUSE")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MOUSE_TYPE);
        }
        MouseDetail mouse = new MouseDetail(
                product,
                request.getSensors(),
                request.getNumberOfNodes(),
                request.getSwitchType(),
                request.getSwitchLife(),
                request.getPollingRate(),
                request.getSoftware(),
                request.getConnect(),
                request.isWirelessConnect(),
                request.getBattery(),
                request.getLed()
        );
        mouseRepository.save(mouse);
    }

}
