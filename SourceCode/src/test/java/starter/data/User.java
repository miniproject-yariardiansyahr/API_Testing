package starter.data;

public class User {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    String email = "yari@mail.com";
    String password = "123";
    String fullName = "yari";
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJGdWxsbmFtZSI6InlhcmkiLCJFbWFpbCI6InlhcmlAbWFpbC5jb20ifQ.0z6VRzNv-eTkpn1xxncO-jaOGjpTMjQwxo4SdIWNBcg";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
