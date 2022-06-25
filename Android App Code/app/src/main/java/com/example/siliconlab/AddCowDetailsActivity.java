package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityAddCowDetailsBinding;

public class AddCowDetailsActivity extends AppCompatActivity {
    ActivityAddCowDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCowDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mCowName.setText(getIntent().getStringExtra("name"));
        binding.submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}