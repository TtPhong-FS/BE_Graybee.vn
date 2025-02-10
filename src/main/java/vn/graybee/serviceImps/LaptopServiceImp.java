package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.LaptopDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.LaptopRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.laptop.LaptopDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class LaptopServiceImp implements ProductDetailService {

    private final LaptopRepository laptopRepository;

    public LaptopServiceImp(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getCategoryName().equalsIgnoreCase("laptop")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_LAPTOP_TYPE);
        }
        LaptopDetailCreateRequest laptopDto = (LaptopDetailCreateRequest) request;
        LaptopDetail laptop = new LaptopDetail(
                product,
                laptopDto.getCpu(),
                laptopDto.getRam(),
                laptopDto.getDemand(),
                laptopDto.getStorage(),
                laptopDto.getOperatingSystem(),
                laptopDto.getVga(),
                laptopDto.getMonitor(),
                laptopDto.getPorts(),
                laptopDto.getKeyboard(),
                laptopDto.getWirelessConnectivity(),
                laptopDto.getAudio(),
                laptopDto.getWebcam(),
                laptopDto.getBattery(),
                laptopDto.getMaterial(),
                laptopDto.getConfidentiality()
        );
        laptopRepository.save(laptop);
    }

    @Override
    public String getDetailType() {
        return "LaptopDetailCreateRequest";
    }

}
