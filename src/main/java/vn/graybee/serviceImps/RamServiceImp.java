package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.Product;
import vn.graybee.models.Ram;
import vn.graybee.repositories.CategoryRepository;
import vn.graybee.repositories.ManufacturerRepository;
import vn.graybee.repositories.ProductRepository;
import vn.graybee.repositories.RamRepository;
import vn.graybee.requests.rams.RamCreateRequest;
import vn.graybee.services.RamService;

import java.util.Optional;

@Service
public class RamServiceImp implements RamService {

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final ProductRepository productRepository;

    private final RamRepository ramRepository;

    public RamServiceImp(CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, ProductRepository productRepository, RamRepository ramRepository) {
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
        this.ramRepository = ramRepository;
    }

    @Override
    public void insertRam(RamCreateRequest request) {
        Optional<Product> product = productRepository.findById(request.getProductId());
//        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
//        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(request.getManufacturerId());
//
//        Product product = new Product(
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                category.get(),
//                manufacturer.get(),
//                request.getName(),
//                request.getConditions(),
//                request.getWarranty(),
//                request.getPrice(),
//                request.getColor(),
//                request.getDescription(),
//                request.getThumbnail()
//        );
//        productRepository.save(product);

        Ram ram = new Ram(
                product.get(),
                request.getRamType(),
                request.getSeries(),
                request.getCapacity(),
                request.getType(),
                request.getSpeed(),
                request.getLatency(),
                request.getVoltage(),
                request.isEcc(),
                request.isHeatDissipation(),
                request.getLed()
        );
        ramRepository.save(ram);
    }

    @Override
    public Optional<Ram> findById(long id) {
        return ramRepository.findById(id);
    }

}
