package vn.graybee.repositories.products;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.graybee.models.products.ProductDocument;


public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {


}
