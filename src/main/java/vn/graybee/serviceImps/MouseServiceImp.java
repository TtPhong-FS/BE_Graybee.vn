package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.MouseDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.business.MouseRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.mouse.MouseDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class MouseServiceImp implements ProductDetailService {


    private final MouseRepository mouseRepository;

    public MouseServiceImp(MouseRepository mouseRepository) {
        this.mouseRepository = mouseRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getName().equalsIgnoreCase("mouse")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MOUSE_TYPE);
        }
        MouseDetailCreateRequest mouseDto = (MouseDetailCreateRequest) request;
        MouseDetail mouse = new MouseDetail(
                product,
                mouseDto.getSensors(),
                mouseDto.getNumberOfNodes(),
                mouseDto.getSwitchType(),
                mouseDto.getSwitchLife(),
                mouseDto.getPollingRate(),
                mouseDto.getSoftware(),
                mouseDto.getConnect(),
                mouseDto.isWirelessConnect(),
                mouseDto.getBattery(),
                mouseDto.getLed()
        );
        mouseRepository.save(mouse);
    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }


    @Override
    public String getDetailType() {
        return "MouseDetailCreateRequest";
    }

}
