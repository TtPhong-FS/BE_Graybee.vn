package vn.graybee.modules.product.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.model.ProductDocument;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.response.publics.products.ProductBasicResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductDocumentService {

    private static final Logger logger = LoggerFactory.getLogger(ProductDocumentService.class);

    private final ElasticsearchClient elasticsearchClient;

    private final ProductRepository productRepository;

    public ProductDocumentService(ElasticsearchClient elasticsearchClient, ProductRepository productRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.productRepository = productRepository;
    }

    public BasicMessageResponse<List<ProductDocument>> search(String keyword) throws IOException {
        SearchResponse<ProductDocument> response = elasticsearchClient.search(
                s -> s.index("products")
                        .query(q -> q
                                .match(m -> m
                                        .field("name")
                                        .query(keyword)
                                )
                        ),
                ProductDocument.class
        );

        List<ProductDocument> responses = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(doc -> new ProductDocument(
                                doc.getId(),
                                doc.getName(),
                                doc.getPrice(),
                                doc.getFinalPrice(),
                                doc.getThumbnail()
                        )
                )
                .collect(Collectors.toList());

        return new BasicMessageResponse<>(200, null, responses);
    }

    public BasicMessageResponse<String> loadProductsPublishedIndexIntoElastic() {
        List<ProductBasicResponse> products = productRepository.getProductPublishedToLoadIntoElastic();

        for (ProductBasicResponse product : products) {
            ProductDocument productDocument = new ProductDocument();
            productDocument.setId(product.getId());
            productDocument.setName(product.getName());
            productDocument.setPrice(product.getPrice());
            productDocument.setFinalPrice(product.getFinalPrice());
            productDocument.setThumbnail(product.getThumbnail());

            try {
                elasticsearchClient.index(
                        i -> i.index("products").id(String.valueOf(productDocument.getId())).document(productDocument)
                );
            } catch (IOException e) {
                throw new BusinessCustomException(ConstantGeneral.general, e.getMessage());
            }

        }

        logger.info("✅ Đã index " + products.size() + " sản phẩm vào Elasticsearch.");

        return new BasicMessageResponse<>(200, "Elasticsearch: Thành công tạo index cho " + products.size() + " sản phẩm!", null);

    }

    public void insertProduct(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setName(product.getName());
        doc.setPrice(product.getPrice());
        doc.setFinalPrice(product.getFinalPrice());
        doc.setThumbnail(product.getThumbnail());

        try {
            elasticsearchClient.index(
                    i -> i.index("products").id(String.valueOf(doc.getId())).document(doc)
            );
        } catch (IOException e) {
            throw new BusinessCustomException(ConstantGeneral.general, e.getMessage());
        }
    }

    public void deleteProduct(long id) {
        try {
            GetRequest getRequest = new GetRequest.Builder()
                    .index("products")
                    .id(String.valueOf(id))
                    .build();

            GetResponse<ProductDocument> getResponse = elasticsearchClient.get(getRequest, ProductDocument.class);

            if (getResponse.found()) {
                elasticsearchClient.delete(d -> d.index("products").id(String.valueOf(id)));
            }

        } catch (IOException e) {
            logger.info("Not found product with id: " + id + " in Elasticsearch, no need to delete.");
        }
    }

}
