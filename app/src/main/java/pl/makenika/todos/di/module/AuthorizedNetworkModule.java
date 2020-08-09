package pl.makenika.todos.di.module;

import com.squareup.moshi.Moshi;

import java.util.Objects;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.makenika.todos.BuildConfig;
import pl.makenika.todos.data.AppPrefs;
import pl.makenika.todos.di.qualifier.AuthorizedHttpClient;
import pl.makenika.todos.net.TodoService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class AuthorizedNetworkModule {
    @Provides
    @Singleton
    @AuthorizedHttpClient
    OkHttpClient provideOkHttpClient(OkHttpClient httpClient, AppPrefs appPrefs) {
        return httpClient
                .newBuilder()
                .addInterceptor(chain -> {
                    final String authHeaderValue = "Bearer " + Objects.requireNonNull(appPrefs.getJwt());
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .header("Authorization", authHeaderValue);
                    return chain.proceed(builder.build());
                })
                .build();
    }

    @Provides
    @Singleton
    TodoService provideTodoService(@AuthorizedHttpClient OkHttpClient httpClient, Moshi moshi) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .build();
        return retrofit.create(TodoService.class);
    }
}
