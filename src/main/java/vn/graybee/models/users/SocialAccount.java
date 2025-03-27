//package vn.graybee.models.users;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "social_accounts")
//public class SocialAccount {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(name = "provider", nullable = false, length = 20)
//    private String provider;
//
//    @Column(name = "provider_id", nullable = false)
//    private int providerId;
//
//    @Column(nullable = false, length = 50)
//    private String email;
//
//    @Column(nullable = false, length = 50)
//    private String name;
//
//    public SocialAccount() {
//    }
//
//    public SocialAccount(User user, String provider, int providerId, String email, String name) {
//        this.user = user;
//        this.provider = provider;
//        this.providerId = providerId;
//        this.email = email;
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getProvider() {
//        return provider;
//    }
//
//    public void setProvider(String provider) {
//        this.provider = provider;
//    }
//
//    public int getProviderId() {
//        return providerId;
//    }
//
//    public void setProviderId(int providerId) {
//        this.providerId = providerId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//}
