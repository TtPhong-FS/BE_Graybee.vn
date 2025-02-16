package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.PcDetail;
import vn.graybee.models.products.Product;
import vn.graybee.projections.publics.PcSummaryProjection;
import vn.graybee.repositories.business.PcRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.pc.PcDetailCreateRequest;
import vn.graybee.response.publics.pc.PcSummaryResponse;
import vn.graybee.services.business.ProductDetailService;
import vn.graybee.utils.HardwareExtractor;

@Service
public class PcServiceImp implements ProductDetailService {


    private final PcRepository pcRepository;

    private final HardwareExtractor extractor;

    public PcServiceImp(PcRepository pcRepository, HardwareExtractor extractor) {
        this.pcRepository = pcRepository;
        this.extractor = extractor;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getName().equalsIgnoreCase("pc")) {
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
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }


    @Override
    public String getDetailType() {
        return "PcDetailCreateRequest";
    }

    public BasicMessageResponse<PcSummaryResponse> findByProductId(long productId) {
        PcSummaryProjection detail = pcRepository.findByProductId(productId).orElseThrow(() -> new CustomNotFoundException("product", ErrorProductConstants.PRODUCT_DOES_NOT_EXISTS));

        PcSummaryResponse pcSummaryResponse = new PcSummaryResponse(detail, extractor);
        return new BasicMessageResponse<>(200, "Product detail: ", pcSummaryResponse);
    }

}
