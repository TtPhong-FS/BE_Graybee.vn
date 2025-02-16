package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.models.collections.RamDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.business.RamRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.response.collections.RamDetailDtoResponse;
import vn.graybee.services.business.ProductDetailService;
import vn.graybee.utils.TextUtils;

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
                TextUtils.capitalize(ramDto.getRamType()),
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
