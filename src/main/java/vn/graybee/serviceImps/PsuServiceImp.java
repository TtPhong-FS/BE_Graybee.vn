package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.PsuDetail;
import vn.graybee.repositories.business.PsuRepository;
import vn.graybee.requests.psu.PsuDetailCreateRequest;
import vn.graybee.services.business.ProductService;
import vn.graybee.services.business.PsuService;

@Service
public class PsuServiceImp implements PsuService {

    private final ProductService productService;

    private final PsuRepository psuRepository;

    public PsuServiceImp(ProductService productService, PsuRepository psuRepository) {
        this.productService = productService;
        this.psuRepository = psuRepository;
    }

    @Override
    @Transactional
    public void createPsuDetail(PsuDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("PSU")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_PSU_TYPE);
        }
        PsuDetail psu = new PsuDetail(
                product,
                request.getMaximumCapacity(),
                request.getMaterial(),
                request.getInputVoltage(),
                request.getInputCurrent(),
                request.getInputFrequency(),
                request.getFanSize(),
                request.getFanSpeed(),
                request.getOperatingTemperature(),
                request.getSignal(),
                request.getStanding(),
                request.getHoursToFailure(),
                request.getNoiseLevel(),
                request.getPercentEfficiency()
        );
        psuRepository.save(psu);
    }

}
