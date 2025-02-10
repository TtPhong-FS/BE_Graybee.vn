package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.PcDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.PcRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.pc.PcDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class PcServiceImp implements ProductDetailService {


    private final PcRepository pcRepository;

    public PcServiceImp(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getCategoryName().equalsIgnoreCase("pc")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_PC_TYPE);
        }
        PcDetailCreateRequest pcDto = (PcDetailCreateRequest) request;
        PcDetail pc = new PcDetail(
                product,
                pcDto.getDemand(),
                pcDto.getCpu(),
                pcDto.getMotherboard(),
                pcDto.getRam(),
                pcDto.getStorage(),
                pcDto.getOperatingSystem(),
                pcDto.getVga(),
                pcDto.getInputPort(),
                pcDto.getOutputPort(),
                pcDto.getCooling(),
                pcDto.getPsu(),
                pcDto.getCaseName()
        );
        pcRepository.save(pc);
    }

    @Override
    public String getDetailType() {
        return "PcDetailCreateRequest";
    }

}
