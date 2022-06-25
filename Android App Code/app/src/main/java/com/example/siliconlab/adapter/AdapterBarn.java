package com.example.siliconlab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siliconlab.R;
import com.example.siliconlab.classes.ModelBarn;

import java.util.List;

public class AdapterBarn extends RecyclerView.Adapter<AdapterBarn.ViewHolder> {

    private List<ModelBarn> barnList;
    private ViewHolder.OnBarnListener mOnBarnListner;

    public AdapterBarn(List<ModelBarn> barnList ,ViewHolder.OnBarnListener onBarnListener) {
        this.barnList = barnList;
        this.mOnBarnListner = onBarnListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(List<ModelBarn> filterllist) {
        barnList = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterBarn.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.barnitem,parent,false);
        return new ViewHolder(view, mOnBarnListner);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarn.ViewHolder holder, int position) {
        int status=barnList.get(position).getBarnState();
        String barn=barnList.get(position).getBarnName();
        holder.setData(status,barn);
    }

    @Override
    public int getItemCount() {
        return barnList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private View view;
        OnBarnListener onBarnListener;
        public ViewHolder(@NonNull View itemView, OnBarnListener monBarnListner) {
            super(itemView);
            textView=itemView.findViewById(R.id.bName);
            view=itemView.findViewById(R.id.mStatus);
            this.onBarnListener=monBarnListner;

            itemView.setOnClickListener(this);
        }

        public void setData(int status,String text) {
            textView.setText(text);
            if(status==1){
                view.setBackgroundResource(R.color.green);
            }
            else{
                view.setBackgroundResource(R.color.red);
            }
        }

        @Override
        public void onClick(View view) {
            onBarnListener.onBarnClick(getAdapterPosition());
        }

        public interface OnBarnListener {
            void onBarnClick(int position);
        }
    }

}
