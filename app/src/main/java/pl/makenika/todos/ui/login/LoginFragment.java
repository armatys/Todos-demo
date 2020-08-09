package pl.makenika.todos.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.transition.TransitionManager;

import dagger.hilt.android.AndroidEntryPoint;
import pl.makenika.todos.R;
import pl.makenika.todos.ui.FormStatus;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private TextView errorTextView;
    private View progressContainer;
    private ViewGroup rootView;

    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorTextView = view.findViewById(R.id.error_text_view);
        progressContainer = view.findViewById(R.id.progress_container);
        rootView = view.findViewById(R.id.root_view);

        view.findViewById(R.id.signup_button).setOnClickListener(v -> {
            NavDirections directions = LoginFragmentDirections.actionLoginFragmentToSignupFragment();
            Navigation.findNavController(v).navigate(directions);
        });

        view.findViewById(R.id.submit_button).setOnClickListener(v -> {
            String email = view.<EditText>findViewById(R.id.email_input).getText().toString();
            String password = view.<EditText>findViewById(R.id.password_input).getText().toString();
            viewModel.onFormSubmitted(email, password);
        });

        viewModel.getFormStatus().observe(getViewLifecycleOwner(), this::handleFormStatus);
    }

    private void handleFormStatus(FormStatus result) {
        TransitionManager.beginDelayedTransition(rootView);

        if (result instanceof FormStatus.Idle) {
            errorTextView.setVisibility(View.GONE);
            progressContainer.setVisibility(View.GONE);
        } else if (result instanceof FormStatus.Sending) {
            errorTextView.setVisibility(View.GONE);
            progressContainer.setVisibility(View.VISIBLE);
        } else if (result instanceof FormStatus.Error) {
            errorTextView.setText(((FormStatus.Error) result).message);
            errorTextView.setVisibility(View.VISIBLE);
            progressContainer.setVisibility(View.GONE);
        } else if (result instanceof FormStatus.Success) {
            NavDirections directions = LoginFragmentDirections.actionLoginFragmentToDashboardFragment();
            Navigation.findNavController(rootView).navigate(directions);
        }
    }
}
