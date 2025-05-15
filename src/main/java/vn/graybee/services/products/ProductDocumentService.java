package vn.graybee.services.products;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Product;
import vn.graybee.models.products.ProductDocument;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.response.publics.products.ProductBasicResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductDocumentService {

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

        System.out.println("✅ Đã index " + products.size() + " sản phẩm vào Elasticsearch.");

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
                DeleteResponse response = elasticsearchClient.delete(d -> d.index("products").id(String.valueOf(id)));

                if (response.result() == Result.NotFound) {
                    throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.record_not_exists);
                }
            }

        } catch (IOException e) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.record_not_exists);
        }

    }


}
