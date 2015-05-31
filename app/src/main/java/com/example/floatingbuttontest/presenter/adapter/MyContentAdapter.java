package com.example.floatingbuttontest.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.model.ContentModel;

import java.util.List;

/**
 * Created by dell on 2015/5/30.
 */
public class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter.MyViewHolder>{
    private List<ContentModel> mContentModelList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener = null;

    public MyContentAdapter(List<ContentModel> mContentModelList) {
        this.mContentModelList = mContentModelList;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyContentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.item_content, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (ContentModel) v.getTag());
                }

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnRecyclerViewItemLongClickListener!=null){
                    mOnRecyclerViewItemLongClickListener.onItemLongClick(v, (ContentModel) v.getTag());
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyContentAdapter.MyViewHolder holder, int position) {
        ContentModel contentItem = mContentModelList.get(position);
        holder.title.setText(contentItem.getTitle());
        holder.time.setText(contentItem.getTime());
        holder.goodTextView.setText(contentItem.getGoodTextView());
        holder.badTextView.setText(contentItem.getBadTextView());
        holder.addressTextView.setText(contentItem.getAddressTextView());
        holder.content.setText(contentItem.getContentTextView());
//Html.fromHtml
        holder.itemView.setTag(mContentModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return mContentModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView time;
        TextView content;
        TextView goodTextView;
        TextView badTextView;
        TextView addressTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            goodTextView = (TextView) itemView.findViewById(R.id.tv_good);
            badTextView = (TextView) itemView.findViewById(R.id.tv_bad);
            addressTextView = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ContentModel contentModel);
    }


    public static interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view, ContentModel contentModel);
    }
}
