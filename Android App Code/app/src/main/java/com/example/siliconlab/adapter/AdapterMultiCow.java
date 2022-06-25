package com.example.siliconlab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siliconlab.R;
import com.example.siliconlab.classes.ModelCow;

import java.util.List;

public class AdapterMultiCow extends RecyclerView.Adapter<AdapterMultiCow.ViewHolder> {

    private List<ModelCow> multiCowList;

    public AdapterMultiCow(List<ModelCow> multiCowList) {
        this.multiCowList = multiCowList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_cow_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cName = multiCowList.get(position).getCowName();
        String cimg = multiCowList.get(position).getImgURI();
    }

    @Override
    public int getItemCount() {
        return multiCowList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.cmName);
            imgView=itemView.findViewById(R.id.cmImg);
        }

    }
}
