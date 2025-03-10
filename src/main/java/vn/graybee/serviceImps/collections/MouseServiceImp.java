package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.MouseDetail;
import vn.graybee.projections.collections.MouseDetailProjection;
import vn.graybee.repositories.collections.MouseRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.MouseDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class MouseServiceImp implements ProductDetailService {

    private final EntityManager entityManager;


    private final MouseRepository mouseRepository;

    public MouseServiceImp(EntityManager entityManager, MouseRepository mouseRepository) {
        this.entityManager = entityManager;
        this.mouseRepository = mouseRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {

        if (!detailType.equalsIgnoreCase("mouse")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_MOUSE_TYPE);
        }
        MouseDetailCreateRequest mouseDto = (MouseDetailCreateRequest) request;
        MouseDetail mouse = new MouseDetail(
                productId,
                mouseDto.getSensors(),
                mouseDto.getNumberOfNodes(),
                mouseDto.getSwitchType(),
                mouseDto.getSwitchLife(),
                mouseDto.getPollingRate(),
                mouseDto.getSoftware(),
                mouseDto.getConnect(),
                mouseDto.isWirelessConnect(),
                mouseDto.getBattery(),
                mouseDto.getLed()
        );
        entityManager.persist(mouse);
    }

    @Override
    public String getDetailType() {
        return "MouseDetailCreateRequest";
    }

    public BasicMessageResponse<List<MouseDetailProjection>> fetchAll() {
        List<MouseDetailProjection> mouses = mouseRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách MOUSE: ", mouses);
    }

}
