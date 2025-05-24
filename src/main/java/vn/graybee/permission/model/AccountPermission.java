package vn.graybee.permission.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "account_permission", uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "permission_id"}))
public class AccountPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permission_id", nullable = false)
    private Integer permissionId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    public AccountPermission() {
    }

    public AccountPermission(Integer permissionId, Long accountId) {
        this.permissionId = permissionId;
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
