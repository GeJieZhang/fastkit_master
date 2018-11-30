package com.example.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.base.R;
import com.example.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;

import java.util.List;


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {


    private static final int VIEW_TYPE = -1;

    private Context context;
    private List<T> mData;

    public BaseAdapter(Context context, List<T> mData) {
        this.context = context;
        this.mData = mData;

    }

    public void updateData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (viewType == VIEW_TYPE) {
            holder = ViewHolder.createViewHolder(context, parent, R.layout.base_empty);
        } else {
            holder = ViewHolder.createViewHolder(context, parent, getLayoutId());
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (getItemViewType(position) == VIEW_TYPE) {
            return;
        } else {


            toBindViewHolder(holder, position, mData);

        }
    }


    @Override
    public int getItemCount() {
        //返回集合的长度
        return mData.size() > 0 ? mData.size() : 1;
    }


    /**
     * 获取条目 View填充的类型
     * 默认返回0
     * 将lists为空返回-1
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        if (mData.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    public abstract int getLayoutId();

    protected abstract void toBindViewHolder(ViewHolder holder, int position, List<T> mData);

}