package vn.graybee.modules.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.comment.model.ReviewComment;
import vn.graybee.modules.comment.service.AdminReviewCommentService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/review-comments")
public class AdminReviewCommentController {

    private final AdminReviewCommentService services;

    public AdminReviewCommentController(AdminReviewCommentService services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ReviewComment>>> fetchAll() {
        return ResponseEntity.ok(services.fetchAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok(services.delete(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<BasicMessageResponse<String>> getCommentById(@PathVariable("id") int id) {
        return ResponseEntity.ok(services.getCommentById(id));
    }

}
