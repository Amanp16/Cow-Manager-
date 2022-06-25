package com.example.siliconlab.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siliconlab.AddBarnActivity;
import com.example.siliconlab.BarnActivity;
import com.example.siliconlab.adapter.AdapterBarn;
import com.example.siliconlab.databinding.FragmentDashboardBinding;
import com.example.siliconlab.classes.ModelBarn;
import com.example.siliconlab.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements AdapterBarn.ViewHolder.OnBarnListener {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    LinearLayoutManager layoutManager;
    List<ModelBarn> mBarnList = new ArrayList<>();
    AdapterBarn mBarnAdapter;
    private FloatingActionButton mFab;
    private View view;
    private User crrUser = new User() ;

    private String newBarn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recbarn.setLayoutManager(layoutManager);

        getData();


        mBarnList.add(new ModelBarn("Barn A",1));
        mBarnList.add(new ModelBarn("Barn B",2));
        mBarnList.add(new ModelBarn("Barn C",1));
        mBarnList.add(new ModelBarn("Barn D",1));
        mBarnList.add(new ModelBarn("Barn E",1));
        mBarnList.add(new ModelBarn("Barn F",2));
        mBarnList.add(new ModelBarn("Barn G",2));
        mBarnList.add(new ModelBarn("Barn H",1));
        mBarnList.add(new ModelBarn("Barn I",1));
        mBarnAdapter= new AdapterBarn(mBarnList,this);
        binding.recbarn.setAdapter(mBarnAdapter);


        binding.mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }


        });


            binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddBarnActivity.class);
                    startActivityForResult(intent,101);
                }
            });


        return root;
    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        List<ModelBarn> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (ModelBarn item : mBarnList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getBarnName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            mBarnAdapter.filterList(filteredlist);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onBarnClick(int position) {
        Intent intent = new Intent(getActivity(), BarnActivity.class);
        intent.putExtra("name",mBarnList.get(position).getBarnName());
        intent.putExtra("state",mBarnList.get(position).getBarnState());
        startActivity(intent);
    }

    private void getData ()
    {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                if ( user.getUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid()) )
//                {
//                    crrUser = user ;
//                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addChildEventListener(childEventListener);

    }

}