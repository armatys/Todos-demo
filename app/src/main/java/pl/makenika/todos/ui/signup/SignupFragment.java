package pl.makenika.todos.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class SignupFragment extends Fragment {
    private TextView errorTextView;
    private View progressContainer;
    private ViewGroup rootView;

    private SignupViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorTextView = view.findViewById(R.id.error_text_view);
        progressContainer = view.findViewById(R.id.progress_container);
        rootView = view.findViewById(R.id.root_view);

        view.findViewById(R.id.login_button).setOnClickListener(v -> {
            NavDirections directions = SignupFragmentDirections.actionSignupFragmentToLoginFragment();
            Navigation.findNavController(v).navigate(directions);
        });

        view.findViewById(R.id.submit_button).setOnClickListener(v -> {
            String name = view.<EditText>findViewById(R.id.name_input).getText().toString();
            String email = view.<EditText>findViewById(R.id.email_input).getText().toString();
            String password = view.<EditText>findViewById(R.id.password_input).getText().toString();
            String confirmedPassword = view.<EditText>findViewById(R.id.password_confirm_input).getText().toString();
            viewModel.onFormSubmitted(name, email, password, confirmedPassword);
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
            String msg = ((FormStatus.Success) result).message;
            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
            NavDirections directions = SignupFragmentDirections.actionSignupFragmentToDashboardFragment();
            Navigation.findNavController(rootView).navigate(directions);
        }
    }
}
