package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.HddDetail;
import vn.graybee.projections.collections.HddDetailProjection;
import vn.graybee.repositories.collections.HddRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.HddDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class HddServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final HddRepository hddRepository;

    public HddServiceImp(EntityManager entityManager, HddRepository hddRepository) {
        this.entityManager = entityManager;
        this.hddRepository = hddRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String categoryName, DetailDtoRequest request) {

        if (!categoryName.equalsIgnoreCase("hdd")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_HDD_TYPE);
        }
        HddDetailCreateRequest hddDto = (HddDetailCreateRequest) request;
        HddDetail hdd = new HddDetail(
                productId,
                hddDto.getCommunicationStandard(),
                hddDto.getCapacity(),
                hddDto.getLifeSpan(),
                hddDto.getReadingSpeed(),
                hddDto.getWritingSpeed(),
                hddDto.getNoiseLevel(),
                hddDto.getCache()
        );
        entityManager.persist(hdd);
    }

    @Override
    public String getDetailType() {
        return "HddDetailCreateRequest";
    }

    public BasicMessageResponse<List<HddDetailProjection>> fetchAll() {
        List<HddDetailProjection> hdds = hddRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách Hdd: ", hdds);
    }

}
