package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityBarnBinding;
import com.example.siliconlab.databinding.ActivityCowBinding;

import org.eazegraph.lib.models.PieModel;

public class CowActivity extends AppCompatActivity {
    ActivityCowBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.acCowName.setText(getIntent().getStringExtra("name"));
        int st= getIntent().getIntExtra("state",1);
        if(st==1){
            binding.alertCard.setVisibility(View.GONE);
            binding.dName.setVisibility(View.GONE);
        }
        else{
            binding.alertCard.setVisibility(View.VISIBLE);
            binding.dName.setVisibility(View.VISIBLE);
        }

        binding.piechart.addPieSlice(new PieModel(
                "R",
                30,
                Color.parseColor("#26A252")));
        binding.piechart.addPieSlice(new PieModel(
                "S",
                20,
                Color.parseColor("#585159")));

    }
}