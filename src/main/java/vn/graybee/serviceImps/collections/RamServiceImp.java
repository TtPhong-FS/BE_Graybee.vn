package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.RamDetail;
import vn.graybee.projections.collections.RamDetailProjection;
import vn.graybee.repositories.collections.RamRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.RamDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class RamServiceImp implements ProductDetailService {

    private final RamRepository ramRepository;

    private final EntityManager entityManager;

    public RamServiceImp(RamRepository ramRepository, EntityManager entityManager) {
        this.ramRepository = ramRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String categoryName, DetailDtoRequest request) {
        if (!categoryName.equalsIgnoreCase("ram")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_RAM_TYPE);
        }
        RamDetailCreateRequest ramDto = (RamDetailCreateRequest) request;
        RamDetail ram = new RamDetail(
                ramDto.getSuitableFor().toUpperCase(),
                ramDto.getSeries(),
                ramDto.getCapacity(),
                ramDto.getType().toUpperCase(),
                ramDto.getSpeed(),
                ramDto.getLatency(),
                ramDto.getVoltage(),
                ramDto.isHeatDissipation(),
                ramDto.getLed());

        ram.setProductId(productId);

        entityManager.persist(ram);

    }

    @Override
    public String getDetailType() {
        return "RamDetailCreateRequest";
    }

    public BasicMessageResponse<List<RamDetailProjection>> fetchAll() {
        List<RamDetailProjection> rams = ramRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách RAM: ", rams);
    }

}
