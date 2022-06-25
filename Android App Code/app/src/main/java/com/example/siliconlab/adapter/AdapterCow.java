package com.example.siliconlab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siliconlab.R;
import com.example.siliconlab.classes.ModelCow;

import java.util.List;

public class AdapterCow extends RecyclerView.Adapter<AdapterCow.ViewHolder> {
    
    private List<ModelCow> cowList;
    private AdapterCow.ViewHolder.OnCowListener mOnCowListner;
    public AdapterCow(List<ModelCow> cowList, ViewHolder.OnCowListener OnCowListner){
        this.cowList = cowList;
        this.mOnCowListner=OnCowListner;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cowitem,parent,false);
        return new ViewHolder(view, mOnCowListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int status=cowList.get(position).getCowState();
        String barn=cowList.get(position).getCowName();
        String id=cowList.get(position).getCowId();
        holder.setData(status,barn,id);
    }

    @Override
    public int getItemCount() {
        return cowList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnCowListener onCowListener;

        private TextView textView;
        private View view;
        public ViewHolder(@NonNull View itemView, OnCowListener mOnCowListner) {
            super(itemView);
            textView=itemView.findViewById(R.id.cName);
            view=itemView.findViewById(R.id.cStatus);
            this.onCowListener=mOnCowListner;

            itemView.setOnClickListener(this);
        }

        public void setData(int status, String cow, String id) {
            textView.setText(cow);
            if(status==1){
                view.setBackgroundResource(R.color.green);
            }
            else{
                view.setBackgroundResource(R.color.red);
            }
        }

        @Override
        public void onClick(View view) {
            onCowListener.onCowClick(getAdapterPosition());
        }

        public interface OnCowListener {
            void onCowClick(int position);
        }
    }

}
