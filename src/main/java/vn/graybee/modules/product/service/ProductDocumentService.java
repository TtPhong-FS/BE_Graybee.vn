package vn.graybee.modules.product.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomServerException;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.model.ProductDocument;
import vn.graybee.modules.product.repository.ProductRepository;

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

    public List<ProductDocument> search(String keyword) {
        try {
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

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(doc -> new ProductDocument(
                                    doc.getId(),
                                    doc.getName(),
                                    doc.getSlug(),
                                    doc.getPrice(),
                                    doc.getFinalPrice(),
                                    doc.getThumbnail()
                            )
                    )
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new CustomServerException(Constants.Common.global, "Đã xảy ra lỗi khi tìm kiếm sản phẩm. Vui lòng thử lại sau");
        }
    }

    public int loadProductsPublishedIndexIntoElastic() {
        List<ProductBasicResponse> products = productRepository.getProductPublishedToLoadIntoElastic();

        for (ProductBasicResponse product : products) {
            ProductDocument productDocument = new ProductDocument();
            productDocument.setId(product.getId());
            productDocument.setSlug(product.getSlug());
            productDocument.setName(product.getName());
            productDocument.setPrice(product.getPrice());
            productDocument.setFinalPrice(product.getFinalPrice());
            productDocument.setThumbnail(product.getThumbnail());

            try {
                elasticsearchClient.index(
                        i -> i.index("products").id(String.valueOf(productDocument.getId())).document(productDocument)
                );
            } catch (IOException e) {
                throw new BusinessCustomException(Constants.Common.global, "Lỗi khi index sản phẩm vào Elasticsearch");
            }

        }

        logger.info("Đã index " + products.size() + " sản phẩm vào Elasticsearch.");

        return products.size();

    }

    public void insertProduct(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setName(product.getName());
        doc.setSlug(product.getSlug());
        doc.setPrice(product.getPrice());
        doc.setFinalPrice(product.getFinalPrice());
        doc.setThumbnail(product.getThumbnail());

        try {
            elasticsearchClient.index(
                    i -> i.index("products").id(String.valueOf(doc.getId())).document(doc)
            );
        } catch (IOException e) {
            throw new BusinessCustomException(Constants.Common.global, "Lỗi khi index sản phẩm vào Elasticsearch");
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
            throw new BusinessCustomException(Constants.Common.global, "Lỗi khi xoá sản phẩm bằng ID trong Elasticsearch");
        }
    }

}
