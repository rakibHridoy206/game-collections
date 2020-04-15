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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupFragment extends Fragment {

    private EditText nameET, emailET, phoneET, passwordET, confirmPasswordET;
    private Button registerBtn;
    private Context context;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private OnAddUserSuccessListener listener;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (OnAddUserSuccessListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("CREATE AN ACCOUNT");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("GameCollectionUsers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameET = view.findViewById(R.id.userNameInput);
        emailET = view.findViewById(R.id.userEmailInput);
        phoneET = view.findViewById(R.id.userPhoneNoInput);
        passwordET = view.findViewById(R.id.userPasswordInput);
        confirmPasswordET = view.findViewById(R.id.userConfirmPasswordInput);
        registerBtn = view.findViewById(R.id.userRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String email = emailET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String password = passwordET.getText().toString();
                final String confirmPassword = confirmPasswordET.getText().toString();

                if(name.isEmpty()){
                    nameET.setError("Enter your name");
                    nameET.requestFocus();
                }else if(email.isEmpty()){
                    emailET.setError("Enter your email");
                    emailET.requestFocus();
                }else if(phone.isEmpty()){
                    phoneET.setError("Enter your mobile number");
                    phoneET.requestFocus();
                }else if(phone.length()!=11){
                    phoneET.setError("Enter your valid mobile number");
                    phoneET.requestFocus();
                }else if(password.isEmpty()){
                    passwordET.setError("Enter a password");
                    passwordET.requestFocus();
                }else if(confirmPassword.isEmpty()){
                    confirmPasswordET.setError("Enter password again");
                    confirmPasswordET.requestFocus();
                }else if (!confirmPassword.equals(password)) {
                    confirmPasswordET.requestFocus();
                    Toast.makeText(getActivity(), "Password doesn't matched. Please re-enter password",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                UserInformation userInformation = new UserInformation(id, name, email, phone, password);
                                databaseReference.child(id).setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show();
                                            listener.onAddUserSuccessfull();
                                        }else{
                                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                                Toast.makeText(context, "User is already registered", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(context, "Unsuccessful registration", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public interface OnAddUserSuccessListener{
        void onAddUserSuccessfull();
    }
}
