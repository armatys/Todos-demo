package pl.makenika.todos.net.request;

import com.squareup.moshi.Json;

public class SignupRequest {
    public final String name;
    public final String email;
    public final String password;
    @Json(name = "password_confirmation")
    public final String passwordConfirmation;

    public SignupRequest(String name, String email, String password, String passwordConfirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
