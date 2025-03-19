package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.categories.TagRepository;

import java.util.List;

@Service
public class TagValidation {

    private final TagRepository tagRepository;

    public TagValidation(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Integer> validateByIds(List<String> tagName) {
        List<Integer> tagIds = tagRepository.getIdByNames(tagName);

        if (tagIds.size() != tagName.size()) {
            throw new BusinessCustomException(
                    ConstantCategory.TAGS,
                    ConstantCategory.TAG_DOES_NOT_EXIST
            );
        }

        return tagIds;
    }

    public void validateNameExists(String tagName) {
        if (tagRepository.validateNameExists(tagName).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.TAG_NAME, ConstantCategory.TAG_NAME_EXISTS);
        }
    }

}
