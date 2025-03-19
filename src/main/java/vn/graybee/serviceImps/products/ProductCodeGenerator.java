package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import vn.graybee.models.products.ProductSequence;
import vn.graybee.repositories.products.ProductSequenceRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ProductCodeGenerator {

    private final ProductSequenceRepository productSequenceRepository;

    public ProductCodeGenerator(ProductSequenceRepository productSequenceRepository) {
        this.productSequenceRepository = productSequenceRepository;
    }

    public String generateProductCode(String categoryName) {

        LocalDate now = LocalDate.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));

        String categoryPart = extractCategoryCode(categoryName);

        ProductSequence sequence = productSequenceRepository.findByCategoryCodeAndYearMonth(categoryPart, yearMonth)
                .orElseGet(() -> createNewSequence(categoryPart, yearMonth));

        int nextNumber = sequence.getLastNumber() + 1;
        sequence.setLastNumber(nextNumber);
        productSequenceRepository.save(sequence);

        String idPart = String.format("%03d", nextNumber);

        return String.format("PRD-%s-%s-%s", categoryPart, yearMonth, idPart);
    }

    private String extractCategoryCode(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return "UNK";
        }

        String normalized = categoryName.toUpperCase();
        return normalized.length() > 3 ? normalized.substring(0, 3) : normalized;
    }

    private ProductSequence createNewSequence(String categoryCode, String yearMonth) {
        ProductSequence newSequence = new ProductSequence();
        newSequence.setCategoryCode(categoryCode);
        newSequence.setYearMonth(yearMonth);
        newSequence.setLastNumber(0); // Bắt đầu từ 0
        return newSequence;
    }

}
