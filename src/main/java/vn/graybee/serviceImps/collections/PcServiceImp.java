package vn.graybee.serviceImps.collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ErrorGeneralConstants;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.models.collections.PcDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.collections.PcRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.PcDetailCreateRequest;
import vn.graybee.response.DetailDtoResponse;
import vn.graybee.response.publics.ProductBasicResponse;
import vn.graybee.response.publics.collections.PcResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;
import vn.graybee.services.products.ProductDetailService;
import vn.graybee.utils.HardwareExtractor;

@Service
public class PcServiceImp implements ProductDetailService {


    private final ProductRepository productRepository;

    private final PcRepository pcRepository;

    private final HardwareExtractor extractor;

    public PcServiceImp(ProductRepository productRepository, PcRepository pcRepository, HardwareExtractor extractor) {
        this.productRepository = productRepository;
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
                pcDto.getSsd(),
                pcDto.getHdd(),
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

    public PcSummaryResponse findByCategoryName_PUBLIC(String categoryName) {
        ProductBasicResponse product = productRepository.findByCategoryName_PUBLIC(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(ErrorProductConstants.GENERAL, ErrorProductConstants.PRODUCT_DOES_NOT_EXISTS));

        PcResponse response = pcRepository.findByProductId(product.getId())
                .orElseThrow(() -> new CustomNotFoundException(ErrorProductConstants.GENERAL, ErrorProductConstants.PRODUCT_DOES_NOT_EXISTS));

        return new PcSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getThumbnail(),
                response,
                extractor
        );
    }

}
