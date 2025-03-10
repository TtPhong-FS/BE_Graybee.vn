package vn.graybee.serviceImps.collections;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.collections.ConstantCollections;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.collections.MonitorDetail;
import vn.graybee.projections.collections.MonitorDetailProjection;
import vn.graybee.repositories.collections.MonitorRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.collections.MonitorDetailCreateRequest;
import vn.graybee.services.products.ProductDetailService;

import java.util.List;

@Service
public class MonitorServiceImp implements ProductDetailService {

    private final EntityManager entityManager;

    private final MonitorRepository monitorRepository;

    public MonitorServiceImp(EntityManager entityManager, MonitorRepository monitorRepository) {
        this.entityManager = entityManager;
        this.monitorRepository = monitorRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(long productId, String detailType, DetailDtoRequest request) {

        if (!detailType.equalsIgnoreCase("monitor")) {
            throw new BusinessCustomException(ConstantCollections.PRODUCT_TYPE_ERROR, ConstantCollections.MISSING_MONITOR_TYPE);
        }
        MonitorDetailCreateRequest monitorDto = (MonitorDetailCreateRequest) request;
        MonitorDetail monitor = new MonitorDetail(
                productId,
                monitorDto.getScreenSize(),
                monitorDto.getScreenType(),
                monitorDto.getPanels(),
                monitorDto.getAspectRatio(),
                monitorDto.isSpeaker(),
                monitorDto.getResolution(),
                monitorDto.getColorDisplay(),
                monitorDto.getPercentColor(),
                monitorDto.getRefreshRate(),
                monitorDto.getPorts(),
                monitorDto.getPowerConsumption(),
                monitorDto.getPowerSaveMode(),
                monitorDto.getPowerOffMode(),
                monitorDto.getVoltage(),
                monitorDto.getSpecialFeature(),
                monitorDto.getLifeSpan(),
                monitorDto.getAccessory()
        );
        entityManager.persist(monitor);
    }

    @Override
    public String getDetailType() {
        return "MonitorDetailCreateRequest";
    }


    public BasicMessageResponse<List<MonitorDetailProjection>> fetchAll() {
        List<MonitorDetailProjection> monitors = monitorRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Danh sách MONITOR: ", monitors);
    }

}
