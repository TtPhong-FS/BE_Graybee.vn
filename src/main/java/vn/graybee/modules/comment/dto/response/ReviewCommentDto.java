package vn.graybee.modules.comment.dto.response;

import java.time.LocalDateTime;

public class ReviewCommentDto {

    private String userName;

    private float rating;

    private String comment;

    private LocalDateTime publishedAt;

    public ReviewCommentDto(String userName, float rating, String comment, LocalDateTime publishedAt) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.publishedAt = publishedAt;
    }

    public ReviewCommentDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

}
