package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.PcDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.PcRepository;
import vn.graybee.requests.pc.PcDetailCreateRequest;
import vn.graybee.services.business.PcService;
import vn.graybee.services.business.ProductService;

@Service
public class PcServiceImp implements PcService {

    private final ProductService productService;

    private final PcRepository pcRepository;

    public PcServiceImp(ProductService productService, PcRepository pcRepository) {
        this.productService = productService;
        this.pcRepository = pcRepository;
    }

    @Override
    @Transactional
    public void createPcDetail(PcDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getCategory().getCategoryName().equals("PC")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_PC_TYPE);
        }
        PcDetail pc = new PcDetail(
                product,
                request.getDemand(),
                request.getCpu(),
                request.getMotherboard(),
                request.getRam(),
                request.getStorage(),
                request.getOperatingSystem(),
                request.getVga(),
                request.getInputPort(),
                request.getOutputPort(),
                request.getCooling(),
                request.getPsu(),
                request.getCaseName()
        );
        pcRepository.save(pc);
    }

}
