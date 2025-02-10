package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.HandheldDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.HandheldRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.handheld.HandheldDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class HandheldServiceImp implements ProductDetailService {

    private final HandheldRepository handheldRepository;


    public HandheldServiceImp(HandheldRepository handheldRepository) {
        this.handheldRepository = handheldRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getCategoryName().equalsIgnoreCase("handheld")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HANDHELD_TYPE);
        }
        HandheldDetailCreateRequest handheldDto = (HandheldDetailCreateRequest) request;
        HandheldDetail handheld = new HandheldDetail(
                product,
                handheldDto.getConnectMode(),
                handheldDto.getNumberOfNodes(),
                handheldDto.getSupport(),
                handheldDto.getUsageTime(),
                handheldDto.getBattery(),
                handheldDto.getCharging(),
                handheldDto.getLed()
        );
        handheldRepository.save(handheld);
    }

    @Override
    public String getDetailType() {
        return "HandheldDetailCreateRequest";
    }

}
