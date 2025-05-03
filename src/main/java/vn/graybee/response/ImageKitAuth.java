package vn.graybee.response;

public class ImageKitAuth {

    private String token;

    private Long expire;

    private String signature;


    public ImageKitAuth(String token, Long expire, String signature) {
        this.token = token;
        this.expire = expire;
        this.signature = signature;
    }

    public ImageKitAuth() {
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
