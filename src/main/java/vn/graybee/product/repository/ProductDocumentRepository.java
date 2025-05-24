package vn.graybee.product.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.graybee.product.model.ProductDocument;


public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {


}
