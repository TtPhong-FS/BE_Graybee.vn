package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.CpuDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.CpuRepository;
import vn.graybee.requests.cpu.CpuDetailCreateRequest;
import vn.graybee.services.business.CpuService;
import vn.graybee.services.business.ProductService;

@Service
public class CpuServiceImp implements CpuService {

    private final ProductService productService;

    private final CpuRepository cpuRepository;

    public CpuServiceImp(ProductService productService, CpuRepository cpuRepository) {
        this.productService = productService;
        this.cpuRepository = cpuRepository;
    }

    @Override
    @Transactional
    public void createCpuDetail(CpuDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getCategory().getCategoryName().equals("CPU")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_CPU_TYPE);
        }
        CpuDetail cpu = new CpuDetail(
                product,
                request.getSocket(),
                request.getMultiplier(),
                request.getNumberOfStreams(),
                request.getMaximumPerformanceCore(),
                request.getMaximumEfficiencyCore(),
                request.getBasePerformanceCore(),
                request.getBaseEfficiencyCore(),
                request.getConsumption(),
                request.getCache(),
                request.getMotherboardCompatible(),
                request.getMaximumSupportMemory(),
                request.getMaximumBandwidth(),
                request.getMemoryType(),
                request.isGraphicsCore(),
                request.getPciEdition(),
                request.getPciConfiguration(),
                request.getMaximumPciPorts()
        );
        cpuRepository.save(cpu);
    }

}
