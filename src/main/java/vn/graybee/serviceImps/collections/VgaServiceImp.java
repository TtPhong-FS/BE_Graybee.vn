package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.VgaDetail;
import vn.graybee.projections.collections.VgaDetailProjection;
import vn.graybee.repositories.collections.VgaRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.VgaDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class VgaServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final VgaRepository vgaRepository;

    public VgaServiceImp(EntityManager entityManager, VgaRepository vgaRepository) {
        this.entityManager = entityManager;
        this.vgaRepository = vgaRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {

        if (!detailType.equalsIgnoreCase("vga")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_VGA_TYPE);
        }
        VgaDetailCreateRequest vgaDto = (VgaDetailCreateRequest) request;
        VgaDetail vga = new VgaDetail(
                productId,
                vgaDto.getMemorySpeed(),
                vgaDto.getMemory(),
                vgaDto.getMemoryBus(),
                vgaDto.getCudaKernel(),
                vgaDto.getMaximumResolution(),
                vgaDto.getMaximumScreen(),
                vgaDto.getPorts(),
                vgaDto.getClock(),
                vgaDto.getPowerConsumption(),
                vgaDto.getPsuRecommend(),
                vgaDto.isApplicationSupport()
        );
        entityManager.persist(vga);
    }

    @Override
    public String getDetailType() {
        return "VgaDetailCreateRequest";
    }

    public BasicMessageResponse<List<VgaDetailProjection>> fetchAll() {
        List<VgaDetailProjection> vgas = vgaRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách VGA: ", vgas);
    }

}
