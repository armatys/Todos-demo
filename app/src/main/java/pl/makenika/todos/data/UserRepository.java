package pl.makenika.todos.data;

import androidx.annotation.Nullable;

import com.squareup.moshi.Moshi;

import java.util.Objects;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import pl.makenika.todos.net.AuthService;
import pl.makenika.todos.net.request.LoginRequest;
import pl.makenika.todos.net.request.SignupRequest;
import pl.makenika.todos.net.response.AuthResponse;
import retrofit2.HttpException;

public class UserRepository {
    private AppPrefs appPrefs;
    private Moshi moshi;
    private AuthService authService;

    @Inject
    public UserRepository(AppPrefs appPrefs, Moshi moshi, AuthService authService) {
        this.appPrefs = appPrefs;
        this.moshi = moshi;
        this.authService = authService;
    }

    public Single<Boolean> isUserLoggedIn() {
        return Single.fromCallable(() -> appPrefs.getJwt() != null);
    }

    public Single<AuthResult> signup(String name, String email, String password, String passwordConfirmation) {
        SignupRequest request = new SignupRequest(name, email, password, passwordConfirmation);
        return authService.signup(request)
                .flatMap(response -> {
                    if (response.isSuccessful()) {
                        return Single.just(Objects.requireNonNull(response.body()));
                    } else if (response.code() == 422) {
                        try (ResponseBody body = response.errorBody()) {
                            AuthResponse authResponse = moshi
                                    .adapter(AuthResponse.class)
                                    .fromJson(Objects.requireNonNull(body).string());
                            return Single.just(Objects.requireNonNull(authResponse));
                        }
                    } else {
                        return Single.error(new HttpException(response));
                    }
                })
                .doOnSuccess(authResponse -> {
                    if (authResponse.authToken != null) {
                        setAuthToken(authResponse.authToken);
                    }
                })
                .map(authResponse -> {
                    boolean isSuccessful = authResponse.authToken != null;
                    return new AuthResult(isSuccessful, authResponse.message);
                });
    }

    public Single<AuthResult> login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return authService.login(request)
                .flatMap(response -> {
                    if (response.isSuccessful()) {
                        return Single.just(Objects.requireNonNull(response.body()));
                    } else if (response.code() == 401) {
                        try (ResponseBody body = response.errorBody()) {
                            AuthResponse authResponse = moshi
                                    .adapter(AuthResponse.class)
                                    .fromJson(Objects.requireNonNull(body).string());
                            return Single.just(Objects.requireNonNull(authResponse));
                        }
                    } else {
                        return Single.error(new HttpException(response));
                    }
                })
                .doOnSuccess(authResponse -> {
                    if (authResponse.authToken != null) {
                        setAuthToken(authResponse.authToken);
                    }
                })
                .map(authResponse -> {
                    boolean isSuccessful = authResponse.authToken != null;
                    return new AuthResult(isSuccessful, authResponse.message);
                });
    }

    public Completable logout() {
        return Completable.fromAction(() -> {
            appPrefs.setJwt(null);
        });
    }

    private void setAuthToken(@Nullable String token) {
        appPrefs.setJwt(token);
    }
}
