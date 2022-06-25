package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityAddBarnBinding;

public class AddBarnActivity extends AppCompatActivity {
    ActivityAddBarnBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddBarnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBarnActivity.this,AddMultipleCowActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}