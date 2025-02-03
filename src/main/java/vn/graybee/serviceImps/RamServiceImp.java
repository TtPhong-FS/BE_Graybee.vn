package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.RamDetail;
import vn.graybee.repositories.business.RamRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class RamServiceImp implements ProductDetailService {

    private final RamRepository ramRepository;


    public RamServiceImp(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        String name = product.getCategory().getCategoryName().toLowerCase();
        System.out.println(name);
        if (!product.getCategory().getCategoryName().equalsIgnoreCase("ram")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_RAM_TYPE);
        }
        RamDetailCreateRequest ramDto = (RamDetailCreateRequest) request;
        RamDetail ram = new RamDetail(
                product,
                ramDto.getRamType().toUpperCase(),
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
    public String getDetailType() {
        return "RamDetailCreateRequest";
    }

}
