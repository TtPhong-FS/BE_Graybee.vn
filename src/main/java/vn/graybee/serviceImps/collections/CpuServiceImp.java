package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.CpuDetail;
import vn.graybee.projections.collections.CpuDetailProjection;
import vn.graybee.repositories.collections.CpuRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.CpuDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class CpuServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final CpuRepository cpuRepository;

    public CpuServiceImp(EntityManager entityManager, CpuRepository cpuRepository) {
        this.entityManager = entityManager;
        this.cpuRepository = cpuRepository;
    }

    @Override
    public void saveDetail(long productId, String categoryName, DetailDtoRequest request) {
        if (!categoryName.equalsIgnoreCase("cpu")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_CPU_TYPE);
        }
        CpuDetailCreateRequest cpuDto = (CpuDetailCreateRequest) request;
        CpuDetail cpu = new CpuDetail(
                productId,
                cpuDto.getSocket(),
                cpuDto.getMultiplier(),
                cpuDto.getNumberOfStreams(),
                cpuDto.getMaximumPerformanceCore(),
                cpuDto.getMaximumEfficiencyCore(),
                cpuDto.getBasePerformanceCore(),
                cpuDto.getBaseEfficiencyCore(),
                cpuDto.getPowerConsumption(),
                cpuDto.getCache(),
                cpuDto.getMotherboardCompatible(),
                cpuDto.getMaximumBandwidth(),
                cpuDto.getMemoryType(),
                cpuDto.isGraphicsCore()
        );
        entityManager.persist(cpu);
    }

    @Override
    public String getDetailType() {
        return "CpuDetailCreateRequest";
    }

    public BasicMessageResponse<List<CpuDetailProjection>> fetchAll() {
        List<CpuDetailProjection> cpus = cpuRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách Cpu: ", cpus);
    }

}
