package pl.makenika.todos.net.response;

import androidx.annotation.Nullable;

import com.squareup.moshi.Json;

public class AuthResponse {
    @Json(name = "auth_token")
    @Nullable
    public final String authToken;

    public final String message;

    public AuthResponse(@Nullable String authToken, String message) {
        this.authToken = authToken;
        this.message = message;
    }
}
