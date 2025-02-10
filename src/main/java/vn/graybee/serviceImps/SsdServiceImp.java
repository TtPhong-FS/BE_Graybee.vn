package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.SsdDetail;
import vn.graybee.repositories.business.SsdRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.ssd.SsdDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class SsdServiceImp implements ProductDetailService {

    private final SsdRepository ssdRepository;

    public SsdServiceImp(SsdRepository ssdRepository) {
        this.ssdRepository = ssdRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getCategoryName().equalsIgnoreCase("ssd")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_SSD_TYPE);
        }
        SsdDetailCreateRequest ssdDto = (SsdDetailCreateRequest) request;
        SsdDetail ssd = new SsdDetail(
                product,
                ssdDto.getCommunicationStandard(),
                ssdDto.getCapacity(),
                ssdDto.getHoursToFailure(),
                ssdDto.getReadingSpeed(),
                ssdDto.getWritingSpeed(),
                ssdDto.getCommunicationStandard(),
                ssdDto.getStorageTemperature(),
                ssdDto.getOperatingTemperature(),
                ssdDto.getRandomReadingSpeed(),
                ssdDto.getRandomWritingSpeed(),
                ssdDto.getSoftware()
        );
        ssdRepository.save(ssd);

    }

    @Override
    public String getDetailType() {
        return "SsdDetailCreateRequest";
    }

}
