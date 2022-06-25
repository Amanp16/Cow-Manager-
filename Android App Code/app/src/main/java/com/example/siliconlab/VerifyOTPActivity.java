package com.example.siliconlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.siliconlab.databinding.ActivityVerifyOtpactivityBinding;
import com.example.siliconlab.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    ActivityVerifyOtpactivityBinding binding;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String phn , unm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        phn = getIntent().getStringExtra("phoneNO");
        unm = getIntent().getStringExtra("userName");
        sendVerificationCode(phn);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                // Used for formatting digit to be in 2 digits only

                NumberFormat f = new DecimalFormat("00");

                long min = (millisUntilFinished / 60000) % 60;

                long sec = (millisUntilFinished / 1000) % 60;

                binding.textView2.setText(f.format(min) + ':' + f.format(sec));

            }

            // When the task is over it will print 00:00:00 there

            public void onFinish() {

                binding.textView2.setText("00:00");

            }

        }.start();

        binding.validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.editText.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    binding.editText.setError("Enter valid OTP");
                    binding.editText.requestFocus();
                    return;
                }
                //verifying the code entered manually
                verifyCode(code);
            }
        });

    }
    private void sendVerificationCode(String phone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();;
            if(code != null){
                binding.progress.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            createUser();
                        }else{
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private void createUser ()
    {
        User user = new User();
        user.setName(unm);
        user.setMobile(phn);
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        uploadUser(user);
    }

    private void uploadUser ( User user )
    {
        FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).setValue(user);
        moveToActivity();
    }

    private void moveToActivity ()
    {
        Intent intent=new Intent(VerifyOTPActivity.this,BottomNavActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}