package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.adapter.AdapterCow;
import com.example.siliconlab.adapter.AdapterMultiCow;
import com.example.siliconlab.classes.ModelCow;
import com.example.siliconlab.databinding.ActivityAddCowBinding;
import com.example.siliconlab.databinding.ActivityAddMultipleCowBinding;

import java.util.ArrayList;
import java.util.List;

public class AddMultipleCowActivity extends AppCompatActivity {

    List<ModelCow> mCowList = new ArrayList<>();
    AdapterMultiCow adapterMultiCow;
    ActivityAddMultipleCowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAddMultipleCowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCowList.add(new ModelCow("Cow A",1,"0",".."));
        mCowList.add(new ModelCow("Cow B",2,"102",".."));
        mCowList.add(new ModelCow("Cow C",1,"103",".."));
        mCowList.add(new ModelCow("Cow F",2,"106",".."));
        mCowList.add(new ModelCow("Cow G",1,"107",".."));

        adapterMultiCow = new AdapterMultiCow(mCowList);
        binding.multirec.setLayoutManager(new GridLayoutManager(this, 2));
        binding.multirec.setAdapter(adapterMultiCow);

        binding.mutiFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMultipleCowActivity.this,AddCowActivity.class);
                startActivity(intent);
            }
        });
    }
}