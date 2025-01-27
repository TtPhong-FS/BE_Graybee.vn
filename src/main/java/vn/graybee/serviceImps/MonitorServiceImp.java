package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.MonitorDetail;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.MonitorRepository;
import vn.graybee.requests.monitor.MonitorDetailCreateRequest;
import vn.graybee.services.business.MonitorService;
import vn.graybee.services.business.ProductService;

@Service
public class MonitorServiceImp implements MonitorService {

    private final MonitorRepository monitorRepository;

    private final ProductService productService;

    public MonitorServiceImp(MonitorRepository monitorRepository, ProductService productService) {
        this.monitorRepository = monitorRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void createMonitorDetail(MonitorDetailCreateRequest request) {
        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("MONITOR")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_MONITOR_TYPE);
        }
        MonitorDetail monitor = new MonitorDetail(
                product,
                request.getScreenSize(),
                request.getScreenType(),
                request.getPanels(),
                request.getAspectRatio(),
                request.isSpeaker(),
                request.getResolution(),
                request.getColorDisplay(),
                request.getPercentColor(),
                request.getRefreshRate(),
                request.getPorts(),
                request.getPowerConsumption(),
                request.getPowerSaveMode(),
                request.getPowerOffMode(),
                request.getVoltage(),
                request.getSpecialFeature(),
                request.getHoursToFailure(),
                request.getAccessory()
        );
        monitorRepository.save(monitor);
    }

}
