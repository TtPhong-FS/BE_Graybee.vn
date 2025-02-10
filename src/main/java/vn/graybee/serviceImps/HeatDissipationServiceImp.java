package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.HeatDissipationDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.HeatDissipationRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.heatDissipation.HeatDissipationDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class HeatDissipationServiceImp implements ProductDetailService {


    private final HeatDissipationRepository heatDissipationRepository;

    public HeatDissipationServiceImp(HeatDissipationRepository heatDissipationRepository) {
        this.heatDissipationRepository = heatDissipationRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getCategoryName().equalsIgnoreCase("heat dissipation")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HEAT_DISSIPATION_TYPE);
        }
        HeatDissipationDetailCreateRequest heatDto = (HeatDissipationDetailCreateRequest) request;
        HeatDissipationDetail heatDissipation = new HeatDissipationDetail(
                product,
                heatDto.getCpuSocket(),
                heatDto.getSeries(),
                heatDto.getHeatSinkMaterial(),
                heatDto.getSpeed(),
                heatDto.getAirflow(),
                heatDto.getAirPressure(),
                heatDto.getNoiseLevel(),
                heatDto.isApplicationControl(),
                heatDto.getLed()
        );
        heatDissipationRepository.save(heatDissipation);
    }

    @Override
    public String getDetailType() {
        return "HeatDissipationDetailCreateRequest";
    }

}
