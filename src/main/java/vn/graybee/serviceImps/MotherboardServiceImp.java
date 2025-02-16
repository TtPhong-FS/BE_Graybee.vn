package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.MotherBoardDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.business.MotherboardRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.motherboard.MotherboardDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class MotherboardServiceImp implements ProductDetailService {


    private final MotherboardRepository motherboardRepository;

    public MotherboardServiceImp(MotherboardRepository motherboardRepository) {
        this.motherboardRepository = motherboardRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getName().equalsIgnoreCase("motherboard")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MOTHERBOARD_TYPE);
        }
        MotherboardDetailCreateRequest motherboardDto = (MotherboardDetailCreateRequest) request;
        MotherBoardDetail motherboard = new MotherBoardDetail(
                product,
                motherboardDto.getChipset(),
                motherboardDto.getSocket(),
                motherboardDto.getCpuSupport(),
                motherboardDto.getTechnologySupport(),
                motherboardDto.getMemorySupport(),
                motherboardDto.getSlotMemorySupport(),
                motherboardDto.getMaximumMemorySupport(),
                motherboardDto.getIntegratedGraphics(),
                motherboardDto.getSoundSupport(),
                motherboardDto.getLanSupport(),
                motherboardDto.getExpansionSlots(),
                motherboardDto.getStorageSupport(),
                motherboardDto.getUsbSupport(),
                motherboardDto.getWirelessConnectivity(),
                motherboardDto.getOperatingSystemSupport(),
                motherboardDto.getInternalInputOutputConnectivity(),
                motherboardDto.getInternalInputConnectivity(),
                motherboardDto.getInternalOutputConnectivity(),
                motherboardDto.getRearInputConnectivity(),
                motherboardDto.getRearOutputConnectivity(),
                motherboardDto.getRearInputOutputConnectivity(),
                motherboardDto.getSystemMonitoringApplication(),
                motherboardDto.getBios(),
                motherboardDto.getSpecialFeatures(),
                motherboardDto.getUniqueFeatures(),
                motherboardDto.getAccessory()
        );
        motherboardRepository.save(motherboard);

    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }



    @Override
    public String getDetailType() {
        return "MotherboardDetailCreateRequest";
    }

}
