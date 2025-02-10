package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.PsuDetail;
import vn.graybee.repositories.business.PsuRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.psu.PsuDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class PsuServiceImp implements ProductDetailService {


    private final PsuRepository psuRepository;

    public PsuServiceImp(PsuRepository psuRepository) {
        this.psuRepository = psuRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getCategoryName().equalsIgnoreCase("psu")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_PSU_TYPE);
        }
        PsuDetailCreateRequest psuDto = (PsuDetailCreateRequest) request;
        PsuDetail psu = new PsuDetail(
                product,
                psuDto.getMaximumCapacity(),
                psuDto.getMaterial(),
                psuDto.getInputVoltage(),
                psuDto.getInputCurrent(),
                psuDto.getInputFrequency(),
                psuDto.getFanSize(),
                psuDto.getFanSpeed(),
                psuDto.getOperatingTemperature(),
                psuDto.getSignal(),
                psuDto.getStanding(),
                psuDto.getHoursToFailure(),
                psuDto.getNoiseLevel(),
                psuDto.getPercentEfficiency()
        );
        psuRepository.save(psu);

    }

    @Override
    public String getDetailType() {
        return "PsuDetailCreateRequest";
    }

}
