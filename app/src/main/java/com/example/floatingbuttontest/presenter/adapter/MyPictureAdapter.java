package com.example.floatingbuttontest.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.model.ContentModel;
import com.example.floatingbuttontest.model.PictureModel;

import java.util.List;

/**
 * Created by dell on 2015/5/31.
 */
public class MyPictureAdapter extends RecyclerView.Adapter<MyPictureAdapter.MyViewHolder>{
    private List<PictureModel> mPictureModelList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener = null;

    public MyPictureAdapter(List<PictureModel> mPictureModelList) {
        this.mPictureModelList = mPictureModelList;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyPictureAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.item_picture, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (PictureModel) v.getTag());
                }

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnRecyclerViewItemLongClickListener!=null){
                    mOnRecyclerViewItemLongClickListener.onItemLongClick(v, (PictureModel) v.getTag());
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyPictureAdapter.MyViewHolder holder, int position) {
        PictureModel pictureItem = mPictureModelList.get(position);
        holder.title.setText(pictureItem.getTitle());
        holder.time.setText(pictureItem.getTime());
        holder.goodTextView.setText(pictureItem.getGoodTextView());
        holder.badTextView.setText(pictureItem.getBadTextView());
        holder.addressTextView.setText(pictureItem.getAddressTextView());
        holder.picture.setImageBitmap(pictureItem.getPictureBitmap());
        //holder.content.setText(pictureItem.getContentTextView());
//Html.fromHtml
        holder.itemView.setTag(mPictureModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return mPictureModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView time;
        ImageView picture;
        TextView goodTextView;
        TextView badTextView;
        TextView addressTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv2_title);
            time = (TextView) itemView.findViewById(R.id.tv2_time);
            picture = (ImageView) itemView.findViewById(R.id.tv_picture);
            goodTextView = (TextView) itemView.findViewById(R.id.tv2_good);
            badTextView = (TextView) itemView.findViewById(R.id.tv2_bad);
            addressTextView = (TextView) itemView.findViewById(R.id.tv2_address);
        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, PictureModel pictureModel);
    }


    public static interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view, PictureModel pictureModel);
    }

}
