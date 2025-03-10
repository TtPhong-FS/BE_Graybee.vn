package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.LaptopDetail;
import vn.graybee.projections.collections.LaptopDetailProjection;
import vn.graybee.repositories.collections.LaptopRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.LaptopDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class LaptopServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final LaptopRepository laptopRepository;

    public LaptopServiceImp(EntityManager entityManager, LaptopRepository laptopRepository) {
        this.entityManager = entityManager;
        this.laptopRepository = laptopRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {
        if (!detailType.equalsIgnoreCase("laptop")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_LAPTOP_TYPE);
        }
        LaptopDetailCreateRequest laptopDto = (LaptopDetailCreateRequest) request;
        LaptopDetail laptop = new LaptopDetail(
                productId,
                laptopDto.getCpu(),
                laptopDto.getRam(),
                laptopDto.getDemand(),
                laptopDto.getStorage(),
                laptopDto.getOperatingSystem(),
                laptopDto.getVga(),
                laptopDto.getMonitor(),
                laptopDto.getPorts(),
                laptopDto.getKeyboard(),
                laptopDto.getWirelessConnectivity(),
                laptopDto.getAudio(),
                laptopDto.getWebcam(),
                laptopDto.getBattery(),
                laptopDto.getMaterial(),
                laptopDto.getConfidentiality()
        );
        entityManager.persist(laptop);
    }

    @Override
    public String getDetailType() {
        return "LaptopDetailCreateRequest";
    }

    public BasicMessageResponse<List<LaptopDetailProjection>> fetchAll() {
        List<LaptopDetailProjection> laptops = laptopRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách Laptop: ", laptops);
    }

}
