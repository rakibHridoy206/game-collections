package com.example.gamecollections;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private EditText emailET,passET;
    private Button loginBtn;
    private TextView signUpBtn;
    private Context context;

    private FirebaseAuth firebaseAuth;

    private UserAuthListener userAuthListener;
    private SignupListener signupListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        userAuthListener = (UserAuthListener) context;
        signupListener = (SignupListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        emailET = view.findViewById(R.id.emailInputET);
        passET = view.findViewById(R.id.passwordInputET);
        loginBtn = view.findViewById(R.id.loginBtn);
        signUpBtn = view.findViewById(R.id.signUp);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passET.getText().toString();

                if(email.isEmpty()){
                    emailET.setError("Enter Email Address");
                    emailET.requestFocus();
                }else if(password.isEmpty()){
                    passET.setError("Enter Your Password");
                    passET.requestFocus();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userAuthListener.onLoginSuccessful();
                                Toast.makeText(getActivity(), "Successfully Logged In",Toast.LENGTH_SHORT).show();
                                userAuthListener.onLoginSuccessful();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupListener.onSignupClickSuccess();
            }
        });
    }

    public interface UserAuthListener{
        void onLoginSuccessful();
    }

    public interface SignupListener{
        void onSignupClickSuccess();
    }
}
