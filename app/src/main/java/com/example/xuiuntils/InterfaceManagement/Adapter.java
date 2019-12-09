package com.example.xuiuntils.InterfaceManagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter<T extends BaseData> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<T> mDatas;

    ViewCenter mViewCenter;

    Context mContext;

    public Adapter(Context context, List<T> datas, ViewCenter viewCenter){

        mDatas = datas;
        mViewCenter = viewCenter;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mViewCenter.getView(mContext, mDatas.get(viewType).getNumber());
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.itemView instanceof BindData) ((BindData) holder.itemView).initData(mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private RecyclerView.ViewHolder getViewHolder(View view){
        return new RecyclerView.ViewHolder(view) {};
    }

}
