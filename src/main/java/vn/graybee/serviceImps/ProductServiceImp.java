package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.Category;
import vn.graybee.models.Manufacturer;
import vn.graybee.models.Product;
import vn.graybee.repositories.CategoryRepository;
import vn.graybee.repositories.ManufacturerRepository;
import vn.graybee.repositories.ProductRepository;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.services.ProductService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final ProductRepository productRepository;

    public ProductServiceImp(CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductCreateRequest request) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(request.getManufacturerId());

        Product product = new Product(
                LocalDateTime.now(),
                LocalDateTime.now(),
                category.get(),
                manufacturer.get(),
                request.getName(),
                request.getConditions(),
                request.getWarranty(),
                request.getPrice(),
                request.getColor(),
                request.getDescription(),
                request.getThumbnail()
        );
        productRepository.save(product);
    }

}
