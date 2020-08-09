package pl.makenika.todos.di.module;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.makenika.todos.BuildConfig;
import pl.makenika.todos.net.AuthService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@InstallIn(ApplicationComponent.class)
@Module
public class NetworkModule {
    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .header("Accept", "application/json");
                    return chain.proceed(builder.build());
                })
                .build();
    }

    @Provides
    @Singleton
    AuthService provideTodoService(OkHttpClient okHttpClient, Moshi moshi) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit.create(AuthService.class);
    }
}
