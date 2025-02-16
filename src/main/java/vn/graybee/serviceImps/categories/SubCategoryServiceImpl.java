package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.models.categories.SubCategoryDetail;
import vn.graybee.repositories.categories.SubCategoryDetailRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.category.SubCategoryCreateRequest;
import vn.graybee.requests.category.SubCategoryUpdateRequest;
import vn.graybee.services.categories.SubCategoryServices;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryServices {

    private final SubCategoryRepository repository;

    private final SubCategoryDetailRepository subCategoryDetailRepository;

    public SubCategoryServiceImpl(SubCategoryRepository repository, SubCategoryDetailRepository subCategoryDetailRepository) {
        this.repository = repository;
        this.subCategoryDetailRepository = subCategoryDetailRepository;
    }

    @Override
    @Transactional
    public BasicMessageResponse<SubCategory> create(SubCategoryCreateRequest request) {
        // 1. Lưu SubCategory trước để lấy ID
        SubCategory subCategory = repository.save(new SubCategory(request.getName()));

        List<SubCategoryDetail> subCategoryDetails = request.getSubcategoryDetails()
                .stream()
                .map(detailName -> {
                    SubCategoryDetail detail = subCategoryDetailRepository.findByName(detailName)
                            .orElseGet(() -> new SubCategoryDetail(detailName));
                    detail.setSubCategory(subCategory);
                    return detail;
                })
                .collect(Collectors.toList());


        subCategory.getDetails().clear();
        subCategory.getDetails().addAll(subCategoryDetails);

        repository.save(subCategory);

        return new BasicMessageResponse<>(201, "Create SubCategory success", subCategory);
    }

    @Override
    @Transactional
    public BasicMessageResponse<SubCategory> update(int subCategoryId, SubCategoryUpdateRequest request) {
        // 1. Tìm SubCategory hiện tại
        SubCategory subCategory = repository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        // 2. Lấy danh sách SubCategoryDetail cũ
        List<SubCategoryDetail> currentDetails = subCategory.getDetails();

        // 3. Tạo danh sách mới, kiểm tra tránh trùng lặp
        List<SubCategoryDetail> updatedDetails = request.getSubcategoryDetails()
                .stream()
                .map(detailName -> {
                    // Kiểm tra nếu đã tồn tại thì giữ nguyên
                    return currentDetails.stream()
                            .filter(d -> d.getName().equals(detailName))
                            .findFirst()
                            .orElseGet(() -> {
                                // Nếu chưa có thì tạo mới và set subCategory
                                SubCategoryDetail newDetail = new SubCategoryDetail(detailName);
                                newDetail.setSubCategory(subCategory);
                                return newDetail;
                            });
                })
                .collect(Collectors.toList());

        // 4. Cập nhật danh sách details
        subCategory.getDetails().clear(); // Xóa tham chiếu cũ nhưng không xóa DB vì orphanRemoval = false
        subCategory.getDetails().addAll(updatedDetails); // Thêm danh sách mới vào

        // 5. Lưu lại SubCategory
        repository.save(subCategory);

        return new BasicMessageResponse<>(200, "Update SubCategory success", subCategory);
    }


}
