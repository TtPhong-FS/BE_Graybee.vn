package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.others.ErrorGeneralConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.RamDetail;
import vn.graybee.repositories.business.RamRepository;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.services.business.ProductService;
import vn.graybee.services.business.RamService;

import java.util.Optional;

@Service
public class RamServiceImp implements RamService {

    private final RamRepository ramRepository;

    private final ProductService productService;

    public RamServiceImp(RamRepository ramRepository, ProductService productService) {
        this.ramRepository = ramRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public RamDetail createRamDetail(RamDetailCreateRequest request) {

        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("RAM")) {
            throw new BusinessCustomException(ErrorGeneralConstants.PRODUCT_TYPE_ERROR, ErrorGeneralConstants.MISSING_RAM_TYPE);
        }

        RamDetail ramDetail = new RamDetail(
                product,
                request.getRamType().toUpperCase(),
                request.getSeries(),
                request.getCapacity(),
                request.getType().toUpperCase(),
                request.getSpeed(),
                request.getLatency(),
                request.getVoltage(),
                request.isEcc(),
                request.isHeatDissipation(),
                request.getLed()
        );
        return ramRepository.save(ramDetail);
    }

    @Override
    public Optional<RamDetail> findById(long id) {
        return ramRepository.findById(id);
    }

}
