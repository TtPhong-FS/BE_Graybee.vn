package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.collections.CpuDetail;
import vn.graybee.models.products.Product;
import vn.graybee.repositories.business.CpuRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;
import vn.graybee.requests.cpu.CpuDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class CpuServiceImp implements ProductDetailService {


    private final CpuRepository cpuRepository;

    public CpuServiceImp(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }


    @Override
    public void saveDetail(Product product, DetailDtoRequest request) {
        if (!product.getCategory().getName().equals("CPU")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_CPU_TYPE);
        }
        CpuDetailCreateRequest cpuDto = (CpuDetailCreateRequest) request;
        CpuDetail cpu = new CpuDetail(
                product,
                cpuDto.getSocket(),
                cpuDto.getMultiplier(),
                cpuDto.getNumberOfStreams(),
                cpuDto.getMaximumPerformanceCore(),
                cpuDto.getMaximumEfficiencyCore(),
                cpuDto.getBasePerformanceCore(),
                cpuDto.getBaseEfficiencyCore(),
                cpuDto.getConsumption(),
                cpuDto.getCache(),
                cpuDto.getMotherboardCompatible(),
                cpuDto.getMaximumSupportMemory(),
                cpuDto.getMaximumBandwidth(),
                cpuDto.getMemoryType(),
                cpuDto.isGraphicsCore(),
                cpuDto.getPciEdition(),
                cpuDto.getPciConfiguration(),
                cpuDto.getMaximumPciPorts()
        );
        cpuRepository.save(cpu);

    }

    @Override
    public DetailDtoResponse getDetail(Product product) {
        return null;
    }

    @Override
    public String getDetailType() {
        return "CpuDetailCreateRequest";
    }

}
