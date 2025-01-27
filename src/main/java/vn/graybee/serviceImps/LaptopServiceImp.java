package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.LaptopDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.LaptopRepository;
import vn.graybee.requests.laptop.LaptopDetailCreateRequest;
import vn.graybee.services.business.LaptopService;
import vn.graybee.services.business.ProductService;

@Service
public class LaptopServiceImp implements LaptopService {

    private final ProductService productService;

    private final LaptopRepository laptopRepository;

    public LaptopServiceImp(ProductService productService, LaptopRepository laptopRepository) {
        this.productService = productService;
        this.laptopRepository = laptopRepository;
    }

    @Override
    @Transactional
    public void createLaptopDetail(LaptopDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("LAPTOP")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_LAPTOP_TYPE);
        }
        LaptopDetail laptop = new LaptopDetail(
                product,
                request.getCpu(),
                request.getRam(),
                request.getDemand(),
                request.getStorage(),
                request.getOperatingSystem(),
                request.getVga(),
                request.getMonitor(),
                request.getPorts(),
                request.getKeyboard(),
                request.getWirelessConnectivity(),
                request.getAudio(),
                request.getWebcam(),
                request.getBattery(),
                request.getMaterial(),
                request.getConfidentiality()
        );
        laptopRepository.save(laptop);
    }

}
