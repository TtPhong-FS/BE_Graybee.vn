package vn.graybee.serviceImps.collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.HddDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.collections.HddRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.HddDetailCreateRequest;
import vn.graybee.response.DetailDtoResponse;
import vn.graybee.services.products.ProductDetailService;

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
