package vn.graybee.models.events;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.models.BaseModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false, unique = true)
    private String name;

    @Column(length = 200, nullable = false, unique = true)
    private String code;

    private LocalDateTime issue;

    private LocalDateTime expiration;

    public Discount() {
    }

    public Discount(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getIssue() {
        return issue;
    }

    public void setIssue(LocalDateTime issue) {
        this.issue = issue;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

}
