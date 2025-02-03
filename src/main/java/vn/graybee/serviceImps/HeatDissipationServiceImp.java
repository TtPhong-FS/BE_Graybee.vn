package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.HeatDissipationDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.HeatDissipationRepository;
import vn.graybee.requests.heatDissipation.HeatDissipationDetailCreateRequest;
import vn.graybee.services.business.HeatDissipationService;
import vn.graybee.services.business.ProductService;

@Service
public class HeatDissipationServiceImp implements HeatDissipationService {

    private final ProductService productService;

    private final HeatDissipationRepository heatDissipationRepository;

    public HeatDissipationServiceImp(ProductService productService, HeatDissipationRepository heatDissipationRepository) {
        this.productService = productService;
        this.heatDissipationRepository = heatDissipationRepository;
    }

    @Override
    @Transactional
    public void createHeatDissipationDetail(HeatDissipationDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getCategory().getCategoryName().equals("HEAT DISSIPATION")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HEAT_DISSIPATION_TYPE);
        }
        HeatDissipationDetail heatDissipation = new HeatDissipationDetail(
                product,
                request.getCpuSocket(),
                request.getSeries(),
                request.getHeatSinkMaterial(),
                request.getSpeed(),
                request.getAirflow(),
                request.getAirPressure(),
                request.getNoiseLevel(),
                request.isApplicationControl(),
                request.getLed()
        );
        heatDissipationRepository.save(heatDissipation);
    }

}
