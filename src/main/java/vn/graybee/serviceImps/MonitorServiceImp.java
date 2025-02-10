package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.MonitorDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.MonitorRepository;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.monitor.MonitorDetailCreateRequest;
import vn.graybee.services.business.ProductDetailService;

@Service
public class MonitorServiceImp implements ProductDetailService {

    private final MonitorRepository monitorRepository;

    public MonitorServiceImp(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(Product product, DetailDtoRequest request) {

        if (!product.getCategory().getCategoryName().equalsIgnoreCase("monitor")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MONITOR_TYPE);
        }
        MonitorDetailCreateRequest monitorDto = (MonitorDetailCreateRequest) request;
        MonitorDetail monitor = new MonitorDetail(
                product,
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
                monitorDto.getHoursToFailure(),
                monitorDto.getAccessory()
        );
        monitorRepository.save(monitor);
    }

    @Override
    public String getDetailType() {
        return "MonitorDetailCreateRequest";
    }

}
