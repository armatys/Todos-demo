package pl.makenika.todos.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import pl.makenika.todos.R;

@AndroidEntryPoint
public class SignupFragment extends Fragment {
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
        view.findViewById(R.id.login_button).setOnClickListener(v -> {
            NavDirections directions = SignupFragmentDirections.actionSignupFragmentToLoginFragment();
            Navigation.findNavController(v).navigate(directions);
        });
        view.findViewById(R.id.submit_button).setOnClickListener(v -> {
            String email = view.<EditText>findViewById(R.id.email_input).getText().toString();
            String password = view.<EditText>findViewById(R.id.password_input).getText().toString();
            String confirmedPassword = view.<EditText>findViewById(R.id.password_confirm_input).getText().toString();
            viewModel.onFormSubmitted(email, password, confirmedPassword);
        });
    }
}
