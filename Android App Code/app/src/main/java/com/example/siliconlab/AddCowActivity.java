package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityAddCowBinding;

public class AddCowActivity extends AppCompatActivity {
    ActivityAddCowBinding binding;
    String name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.cowName.getEditText().getText().toString();
                id=binding.cowID.getEditText().getText().toString();
                if(name.isEmpty()){
                    binding.cowName.setError("Enter a valid name");
                    binding.cowName.requestFocus();
                    return;
                }
                if(id.isEmpty()){
                    binding.cowID.setError("Enter valid Belt ID");
                    binding.cowID.requestFocus();
                    return;
                }
                Intent intent = new Intent(AddCowActivity.this,AddCowDetailsActivity.class);

                intent.putExtra("name",name);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });

    }
}