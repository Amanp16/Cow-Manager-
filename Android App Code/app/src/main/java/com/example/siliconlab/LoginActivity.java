package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityLoginBinding;
import com.example.siliconlab.databinding.ActivityVerifyOtpactivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String phoneNumber,usrname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(LoginActivity.this,VerifyOTPActivity.class);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber=binding.phone.getEditText().getText().toString();
                usrname=binding.username.getEditText().getText().toString();
                binding.tnc.setPaintFlags(binding.tnc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                if(phoneNumber.isEmpty() || phoneNumber.length()!=10){
                    binding.phone.setError("Enter a valid mobile number");
                    binding.phone.requestFocus();
                    return;
                }
                if(usrname.isEmpty()){
                    binding.username.setError("Enter a valid username");
                    binding.username.requestFocus();
                    return;
                }
                intent.putExtra("phoneNO",phoneNumber);
                intent.putExtra("userName",usrname);
                startActivity(intent);
            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, BottomNavActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}