package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.HddDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.HddRepository;
import vn.graybee.requests.hdd.HddDetailCreateRequest;
import vn.graybee.services.business.HddService;
import vn.graybee.services.business.ProductService;

@Service
public class HddServiceImp implements HddService {

    private final ProductService productService;

    private final HddRepository hddRepository;

    public HddServiceImp(ProductService productService, HddRepository hddRepository) {
        this.productService = productService;
        this.hddRepository = hddRepository;
    }

    @Override
    @Transactional
    public void createHddDetail(HddDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getCategory().getCategoryName().equals("HDD")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HDD_TYPE);
        }
        HddDetail hdd = new HddDetail(
                product,
                request.getCommunicationStandard(),
                request.getCapacity(),
                request.getHoursToFailure(),
                request.getReadingSpeed(),
                request.getWritingSpeed(),
                request.getCommunicationStandard(),
                request.getNoiseLevel(),
                request.getCache(),
                request.getRevolutionPerMinutes()
        );
        hddRepository.save(hdd);
    }

}
