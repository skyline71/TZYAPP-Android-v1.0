package com.example.floatingbuttontest.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.model.ContentModel;
import com.example.floatingbuttontest.presenter.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2015/5/29.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<String> mList = null;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener = null;

    public HomeAdapter(List<String> mList) {
        this.mList = mList;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.item_home, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, (String) v.getTag());
                }

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnRecyclerViewItemLongClickListener!=null){
                    mOnRecyclerViewItemLongClickListener.onItemLongClick(v, (String) v.getTag());
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
        //�����ݱ�����itemView��Tag�У��Ա���ʱ���л�ȡ
        holder.itemView.setTag(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //�˷���������Ƿ�Ҫ��
    public void addItems(List<String> items)
    {
        if (items == null)
            return;
        this.mList.addAll(0, items);
        this.notifyItemRangeInserted(0, items.size());
    }
    /*@Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //ע������ʹ��getTag������ȡ����
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }

    }*/

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
            mImageView = (ImageView) view.findViewById(R.id.id_item_icon);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }


    public static interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view, String data);
    }


}

