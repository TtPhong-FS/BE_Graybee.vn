package vn.graybee.auth.dto.response;

public class AuthDto {

    private String token;


    public AuthDto() {
    }

    public AuthDto(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
