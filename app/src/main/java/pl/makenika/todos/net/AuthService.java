package pl.makenika.todos.net;

import io.reactivex.rxjava3.core.Single;
import pl.makenika.todos.net.request.LoginRequest;
import pl.makenika.todos.net.request.SignupRequest;
import pl.makenika.todos.net.response.AuthResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/login")
    Single<Response<AuthResponse>> login(@Body LoginRequest request);

    @POST("/signup")
    Single<Response<AuthResponse>> signup(@Body SignupRequest request);
}
