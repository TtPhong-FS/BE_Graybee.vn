package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.MotherBoardDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.MotherboardRepository;
import vn.graybee.requests.motherboard.MotherboardDetailCreateRequest;
import vn.graybee.services.business.MotherboardService;
import vn.graybee.services.business.ProductService;

@Service
public class MotherboardServiceImp implements MotherboardService {

    private final ProductService productService;

    private final MotherboardRepository motherboardRepository;

    public MotherboardServiceImp(ProductService productService, MotherboardRepository motherboardRepository) {
        this.productService = productService;
        this.motherboardRepository = motherboardRepository;
    }

    @Override
    @Transactional
    public void createMotherboardDetail(MotherboardDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("MOTHERBOARD")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MOTHERBOARD_TYPE);
        }
        MotherBoardDetail motherboard = new MotherBoardDetail(
                product,
                request.getChipset(),
                request.getSocket(),
                request.getCpuSupport(),
                request.getTechnologySupport(),
                request.getMemorySupport(),
                request.getSlotMemorySupport(),
                request.getMaximumMemorySupport(),
                request.getIntegratedGraphics(),
                request.getSoundSupport(),
                request.getLanSupport(),
                request.getExpansionSlots(),
                request.getStorageSupport(),
                request.getUsbSupport(),
                request.getWirelessConnectivity(),
                request.getOperatingSystemSupport(),
                request.getInternalInputOutputConnectivity(),
                request.getInternalInputConnectivity(),
                request.getInternalOutputConnectivity(),
                request.getRearInputConnectivity(),
                request.getRearOutputConnectivity(),
                request.getRearInputOutputConnectivity(),
                request.getSystemMonitoringApplication(),
                request.getBios(),
                request.getSpecialFeatures(),
                request.getUniqueFeatures(),
                request.getAccessory()
        );
        motherboardRepository.save(motherboard);
    }

}
