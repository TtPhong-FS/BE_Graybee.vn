package vn.graybee.modules.product.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.graybee.modules.product.model.ProductDocument;


public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {


}
