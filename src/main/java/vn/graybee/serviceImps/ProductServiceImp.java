package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.business.Category;
import vn.graybee.models.business.Manufacturer;
import vn.graybee.models.business.Product;
import vn.graybee.repositories.business.CategoryRepository;
import vn.graybee.repositories.business.ManufacturerRepository;
import vn.graybee.repositories.business.ProductRepository;
import vn.graybee.requests.product.ProductCreateRequest;
import vn.graybee.response.ProductResponseByCategoryId;
import vn.graybee.services.business.ProductService;

import java.time.LocalDateTime;
import java.util.List;
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
    public Product createProduct(ProductCreateRequest request) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(request.getManufacturerId());

        Product product = new Product(
                LocalDateTime.now(),
                LocalDateTime.now(),
                request.getModel(),
                request.getProductType().toUpperCase(),
                request.getName(),
                request.getConditions().toUpperCase(),
                request.getWarranty(),
                request.getWeight(),
                request.getDimension(),
                request.getPrice(),
                request.getColor(),
                request.getDescription(),
                request.getThumbnail()
        );
        product.setCategory(category.get());
        product.setManufacturer(manufacturer.get());
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductResponseByCategoryId> findProductsByCategoryId(long id) {
        return productRepository.findProductByCategory_Id(id);
    }

}
