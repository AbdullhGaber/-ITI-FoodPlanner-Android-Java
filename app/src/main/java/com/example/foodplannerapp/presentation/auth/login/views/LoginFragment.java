package com.example.foodplannerapp.presentation.auth.login.views;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.FragmentLoginBinding;
import com.example.foodplannerapp.presentation.activities.FoodActivity;
import com.example.foodplannerapp.presentation.auth.login.presenter.LoginPresenter;
import com.example.foodplannerapp.presentation.utils.Dialogs;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment implements LoginView {

    @Inject
    LoginPresenter presenter;

    FragmentLoginBinding binding;
    private GoogleSignInClient googleSignInClient;

    // FIX 1: Use the modern Activity Result Launcher
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleGoogleSignInResult(task);
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGoogleSignIn();

        // Listeners
        setOnLoginButtonClickListener();
        setOnRegisterRedirectClick();
        setOnContinueAsAGuestClick();
        setOnGoogleSignInClick();
    }

    private void setOnContinueAsAGuestClick() {
        binding.guestModeTv.setOnClickListener((v) -> {
            presenter.guestMode();
        });
    }

    private void initGoogleSignIn() {
        // FIX 2: You MUST use the "Web Client ID" from Firebase Console here.
        // Do NOT use the Android Client ID.
        String webClientId = getString(R.string.default_web_client_id);
        // Note: Google Services plugin usually generates this string resource automatically.
        // If R.string.default_web_client_id is red, paste the hardcoded string from Step 1.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void setOnGoogleSignInClick() {
        // FIX 3: Click listener for the new custom button
        binding.btnGoogleSignin.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        // Always sign out first to allow account selection
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        });
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null && account.getIdToken() != null) {
                // Pass the ID Token to presenter -> repository -> firebase
                presenter.loginWithGoogle(account.getIdToken());
            }
        } catch (ApiException e) {
            // Common error: 12500 (SHA-1 missing), 10 (Dev config error)
            onLoginFailed("Google Sign-In Failed", "Error Code: " + e.getStatusCode());
        }
    }

    // --- Rest of your existing code remains the same ---

    private void setOnRegisterRedirectClick() {
        binding.redirectRegisterClickable.setOnClickListener(
                (v) -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment)
        );
    }
    private void setOnLoginButtonClickListener() {
        binding.loginButton.setOnClickListener(
                (v) -> {
                    String email = binding.loginEmailEt.getText().toString().trim();
                    String password = binding.loginPasswordEt.getText().toString().trim();

                    if (validateInputs(email, password)) {
                        presenter.login(email, password);
                    }
                }
        );
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;
        binding.loginEmailLayout.setError(null);
        binding.loginPasswordLayout.setError(null);

        if (email.isEmpty()) {
            binding.loginEmailLayout.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.loginEmailLayout.setError("Please enter a valid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.loginPasswordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            binding.loginPasswordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(getActivity(), FoodActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onLoginFailed(String title, String message) {
        Dialogs.showAlertDialog(requireContext(), title, message);
    }

    @Override
    public void showProgressbar() {
        binding.progressBar3.setVisibility(VISIBLE);
        binding.loadingOverlay.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        binding.progressBar3.setVisibility(GONE);
        binding.loadingOverlay.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}