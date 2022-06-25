package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.siliconlab.databinding.ActivityBarnBinding;
import com.example.siliconlab.adapter.AdapterCow;
import com.example.siliconlab.classes.ModelCow;

import java.util.ArrayList;
import java.util.List;

public class BarnActivity extends AppCompatActivity implements AdapterCow.ViewHolder.OnCowListener {
    ActivityBarnBinding binding;

    LinearLayoutManager layoutManager;
    List<ModelCow> mCowList = new ArrayList<>();
    AdapterCow mCowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBarnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.reccow.setLayoutManager(layoutManager);

        mCowList.add(new ModelCow("Cow A",1,"0",".."));
        mCowList.add(new ModelCow("Cow B",2,"102",".."));
        mCowList.add(new ModelCow("Cow C",1,"103",".."));
        mCowList.add(new ModelCow("Cow D",1,"104",".."));
        mCowList.add(new ModelCow("Cow E",2,"105",".."));
        mCowList.add(new ModelCow("Cow F",2,"106",".."));
        mCowList.add(new ModelCow("Cow G",1,"107",".."));

        mCowAdapter=new AdapterCow(mCowList,this);
        binding.reccow.setAdapter(mCowAdapter);


        binding.txt.setText(getIntent().getStringExtra("name"));
        int status = getIntent().getIntExtra("state",1);
        if(status==1) binding.statusIcon.setImageResource(R.drawable.shielddone);
        else binding.statusIcon.setImageResource(R.drawable.ic_baseline_error_24);

        binding.cowFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarnActivity.this,AddCowActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCowClick(int position) {
        Intent intent = new Intent(BarnActivity.this, CowActivity.class);
        intent.putExtra("name",mCowList.get(position).getCowName());
        intent.putExtra("state",mCowList.get(position).getCowState());
        startActivity(intent);
    }
}