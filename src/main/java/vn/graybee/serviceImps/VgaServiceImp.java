package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.VgaDetail;
import vn.graybee.repositories.business.VgaRepository;
import vn.graybee.requests.vga.VgaDetailCreateRequest;
import vn.graybee.services.business.ProductService;
import vn.graybee.services.business.VgaService;

@Service
public class VgaServiceImp implements VgaService {

    private final ProductService productService;

    private final VgaRepository vgaRepository;

    public VgaServiceImp(ProductService productService, VgaRepository vgaRepository) {
        this.productService = productService;
        this.vgaRepository = vgaRepository;
    }

    @Override
    @Transactional
    public void createVgaDetail(VgaDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getCategory().getCategoryName().equals("VGA")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_VGA_TYPE);
        }
        VgaDetail vga = new VgaDetail(
                product,
                request.getMemorySpeed(),
                request.getMemoryProtocol(),
                request.getMaximumResolution(),
                request.getMultipleScreen(),
                request.getProtocols(),
                request.getGpuClock(),
                request.getBusStandard(),
                request.getNumberOfProcessingUnit(),
                request.getPowerConsumption(),
                request.getPsuRecommend(),
                request.getDirectx(),
                request.isApplicationSupport()
        );
        vgaRepository.save(vga);
    }

}
