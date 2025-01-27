package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.SsdDetail;
import vn.graybee.repositories.business.SsdRepository;
import vn.graybee.requests.ssd.SsdDetailCreateRequest;
import vn.graybee.services.business.ProductService;
import vn.graybee.services.business.SsdService;

@Service
public class SsdServiceImp implements SsdService {

    private final ProductService productService;

    private final SsdRepository ssdRepository;

    public SsdServiceImp(ProductService productService, SsdRepository ssdRepository) {
        this.productService = productService;
        this.ssdRepository = ssdRepository;
    }

    @Override
    @Transactional
    public void createSsdDetail(SsdDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("SSD")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_SSD_TYPE);
        }
        SsdDetail ssd = new SsdDetail(
                product,
                request.getCommunicationStandard(),
                request.getCapacity(),
                request.getHoursToFailure(),
                request.getReadingSpeed(),
                request.getWritingSpeed(),
                request.getCommunicationStandard(),
                request.getStorageTemperature(),
                request.getOperatingTemperature(),
                request.getRandomReadingSpeed(),
                request.getRandomWritingSpeed(),
                request.getSoftware()
        );
        ssdRepository.save(ssd);
    }

}
