package vn.graybee.serviceImps.collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.models.collections.RamDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.collections.RamRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.RamDetailCreateRequest;
import vn.graybee.response.DetailDtoResponse;
import vn.graybee.response.collections.RamDetailDtoResponse;
import vn.graybee.services.products.ProductDetailService;

@Service
public class RamServiceImp implements ProductDetailService {

    private final RamRepository ramRepository;


    public RamServiceImp(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getName().equalsIgnoreCase("ram")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_RAM_TYPE);
        }
        RamDetailCreateRequest ramDto = (RamDetailCreateRequest) request;
        RamDetail ram = new RamDetail(
                product,
                ramDto.getSuitableFor().toUpperCase(),
                ramDto.getSeries(),
                ramDto.getCapacity(),
                ramDto.getType().toUpperCase(),
                ramDto.getSpeed(),
                ramDto.getLatency(),
                ramDto.getVoltage(),
                ramDto.isEcc(),
                ramDto.isHeatDissipation(),
                ramDto.getLed());
        ramRepository.save(ram);
    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        RamDetail ramDetail = ramRepository.findById(product.getId()).orElseThrow(() -> new CustomNotFoundException("", "RamDetail not found"));
        return new RamDetailDtoResponse(ramDetail);
    }

    @Override
    public String getDetailType() {
        return "RamDetailCreateRequest";
    }

}
