package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.HddDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.business.HddRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.hdd.HddDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class HddServiceImp implements ProductDetailService {

    private final HddRepository hddRepository;

    public HddServiceImp(HddRepository hddRepository) {
        this.hddRepository = hddRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getName().equalsIgnoreCase("hdd")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_HDD_TYPE);
        }
        HddDetailCreateRequest hddDto = (HddDetailCreateRequest) request;
        HddDetail hdd = new HddDetail(
                product,
                hddDto.getCommunicationStandard(),
                hddDto.getCapacity(),
                hddDto.getHoursToFailure(),
                hddDto.getReadingSpeed(),
                hddDto.getWritingSpeed(),
                hddDto.getCommunicationStandard(),
                hddDto.getNoiseLevel(),
                hddDto.getCache(),
                hddDto.getRevolutionPerMinutes()
        );
        hddRepository.save(hdd);
    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }


    @Override
    public String getDetailType() {
        return "HddDetailCreateRequest";
    }

}
