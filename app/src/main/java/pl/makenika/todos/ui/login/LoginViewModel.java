package pl.makenika.todos.ui.login;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pl.makenika.todos.data.UserRepository;
import pl.makenika.todos.di.qualifier.IoScheduler;
import pl.makenika.todos.ui.data.FormStatus;

public class LoginViewModel extends ViewModel {
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<FormStatus> formStatus = new MutableLiveData<>(new FormStatus.Idle());

    private Scheduler ioScheduler;
    private UserRepository userRepository;

    @ViewModelInject
    public LoginViewModel(@IoScheduler Scheduler ioScheduler, UserRepository userRepository) {
        this.ioScheduler = ioScheduler;
        this.userRepository = userRepository;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public void onFormSubmitted(String email, String password) {
        formStatus.setValue(new FormStatus.Sending());

        Disposable disposable = userRepository
                .login(email, password)
                .subscribeOn(ioScheduler)
                .subscribe(signupResult -> {
                    if (signupResult.isSuccessful) {
                        FormStatus status = new FormStatus.Success(signupResult.message);
                        formStatus.postValue(status);
                    } else {
                        FormStatus status = new FormStatus.Error(signupResult.message);
                        formStatus.postValue(status);
                    }
                }, throwable -> {
                    FormStatus status = new FormStatus.Error("Could not log in. Please try again later.");
                    formStatus.postValue(status);
                });
        disposables.add(disposable);
    }

    public LiveData<FormStatus> getFormStatus() {
        return formStatus;
    }
}
