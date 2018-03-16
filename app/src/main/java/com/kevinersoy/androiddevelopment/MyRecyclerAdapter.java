package com.kevinersoy.androiddevelopment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kevinersoy on 3/15/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    private final Context mContext;
    private final List<ListItemInfo> mItems;
    private final LayoutInflater mLayoutInflater;
    private int lastPosition = -1;

    public MyRecyclerAdapter(Context context, List<ListItemInfo> items){
        mContext = context;
        mItems = items;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemInfo item = mItems.get(position);
        holder.mTextTitle.setText(item.getTitle());
        holder.mTextSubtitleOne.setText(item.getSubtitleOne());
        holder.mTextSubtitleTwo.setText(item.getSubtitleTwo());

        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        viewToAnimate.startAnimation(
                AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom));
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //using inner class to define ViewHolder since it will only be used with this Adapter
        public final TextView mTextTitle;
        public final TextView mTextSubtitleOne;
        public final TextView mTextSubtitleTwo;
        public int mId;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView)itemView.findViewById(R.id.text_card_title);
            mTextSubtitleOne = (TextView)itemView.findViewById(R.id.text_sub_one);
            mTextSubtitleTwo = (TextView)itemView.findViewById(R.id.text_sub_two);

            //setting onClickListener here on the parent view because no child view is clickable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //no click listener for now

                }
            });
        }
    }
}
