package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Product;
import vn.graybee.models.business.RamDetail;
import vn.graybee.repositories.business.RamRepository;
import vn.graybee.requests.ram.RamDetailCreateRequest;
import vn.graybee.services.business.ProductService;
import vn.graybee.services.business.RamDetailService;

import java.util.Optional;

@Service
public class RamDetailServiceImp implements RamDetailService {

    private final RamRepository ramRepository;

    private final ProductService productService;

    public RamDetailServiceImp(RamRepository ramRepository, ProductService productService) {
        this.ramRepository = ramRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void createRamDetail(RamDetailCreateRequest request) {

        Product product = productService.createProduct(request);
        if (!product.getProductType().equals("RAM")) {
            throw new BusinessCustomException("Invalid Type: Product is not Ram");
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
        ramRepository.save(ramDetail);
    }

    @Override
    public Optional<RamDetail> findById(long id) {
        return ramRepository.findById(id);
    }

}
