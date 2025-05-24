package vn.graybee.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.comment.model.ReviewComment;
import vn.graybee.comment.service.ReviewCommentService_admin;
import vn.graybee.common.dto.BasicMessageResponse;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/review-comments")
public class AdminReviewCommentController {

    private final ReviewCommentService_admin services;

    public AdminReviewCommentController(ReviewCommentService_admin services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<ReviewComment>>> fetchAll() {
        return ResponseEntity.ok(services.fetchAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> delete(@RequestParam("id") int id) {
        return ResponseEntity.ok(services.delete(id));
    }

    @GetMapping("/get/comment")
    public ResponseEntity<BasicMessageResponse<String>> getCommentById(@RequestParam("id") int id) {
        return ResponseEntity.ok(services.getCommentById(id));
    }

}
