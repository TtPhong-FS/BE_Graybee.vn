package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.SsdDetail;
import vn.graybee.projections.collections.SsdDetailProjection;
import vn.graybee.repositories.collections.SsdRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.SsdDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class SsdServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final SsdRepository ssdRepository;

    public SsdServiceImp(EntityManager entityManager, SsdRepository ssdRepository) {
        this.entityManager = entityManager;
        this.ssdRepository = ssdRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String categoryName, DetailDtoRequest request) {
        if (!categoryName.equalsIgnoreCase("ssd")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_SSD_TYPE);
        }
        SsdDetailCreateRequest ssdDto = (SsdDetailCreateRequest) request;
        SsdDetail ssd = new SsdDetail(
                productId,
                ssdDto.getCommunicationStandard(),
                ssdDto.getCapacity(),
                ssdDto.getLifeSpan(),
                ssdDto.getReadingSpeed(),
                ssdDto.getWritingSpeed(),
                ssdDto.getStorageTemperature(),
                ssdDto.getOperatingTemperature(),
                ssdDto.getRandomReadingSpeed(),
                ssdDto.getRandomWritingSpeed(),
                ssdDto.getSoftware()
        );
        entityManager.persist(ssd);


    }

    @Override
    public String getDetailType() {
        return "SsdDetailCreateRequest";
    }

    public BasicMessageResponse<List<SsdDetailProjection>> fetchAll() {
        List<SsdDetailProjection> ssds = ssdRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách SSD: ", ssds);
    }

}
