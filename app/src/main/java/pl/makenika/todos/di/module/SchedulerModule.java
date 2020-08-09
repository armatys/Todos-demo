package pl.makenika.todos.di.module;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pl.makenika.todos.di.qualifier.IoScheduler;
import pl.makenika.todos.di.qualifier.MainScheduler;

@Module
@InstallIn(ApplicationComponent.class)
public class SchedulerModule {
    @Provides
    @MainScheduler
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @IoScheduler
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }
}
