package vn.graybee.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favourites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_uid", "product_id"}))
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uid", nullable = false)
    private Integer userUid;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    public Favourite() {
    }

    public Favourite(Integer userUid, Long productId) {
        this.userUid = userUid;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserUid() {
        return userUid;
    }

    public void setUserUid(Integer userUid) {
        this.userUid = userUid;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
