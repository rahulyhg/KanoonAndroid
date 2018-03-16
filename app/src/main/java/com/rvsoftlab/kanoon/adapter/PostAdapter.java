package com.rvsoftlab.kanoon.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.helper.Constant;
import com.rvsoftlab.kanoon.model.Posts;
import java.util.List;

/**
 * Created by RVishwakarma on 3/6/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context mContext;
    private List<Posts> mPost;
    private List<Object> messageList;

    /*public PostAdapter(Context context, List<Posts> list) {
        this.mContext = context;
        this.mPost = list;
    }*/

    public PostAdapter(Context context, List<Object> mlist){
        this.mContext = context;
        this.messageList = mlist;
    }

    /*@Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        *//*Log.d(TAG,""+viewType);
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_list_item_layout,parent,false);
        return new ImageViewHolder(view);*//*
        switch (viewType){
            case Constant.VIEW_TYPE.NEW_POST:
                return new NewPostViewHolder(LayoutInflater.from(mContext).inflate(R.layout.new_post_list_item_layout,parent,false));
                break;
            case Constant.VIEW_TYPE.IMAGE_POSTS:
                return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.post_list_item_layout,parent,false));
                break;
                default:
                    return null;
        }
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case Constant.VIEW_TYPE.NEW_POST:
                return new NewPostViewHolder(LayoutInflater.from(mContext).inflate(R.layout.new_post_list_item_layout,parent,false));
            case Constant.VIEW_TYPE.IMAGE_POSTS:
                return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.post_list_item_layout,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case Constant.VIEW_TYPE.IMAGE_POSTS:
                bindImagePost(holder,position);
                break;
            case Constant.VIEW_TYPE.VIDEO_POST:
                break;
        }
    }

    private void bindImagePost(RecyclerView.ViewHolder holder, int position) {
        final ImageViewHolder imageViewHolder = (ImageViewHolder)holder;
        Posts data = (Posts) messageList.get(position);
        setPostImage(imageViewHolder,data);
        imageViewHolder.postCaption.setText(data.getCaption());
        imageViewHolder.userName.setText(data.getUser().getUserName());
        imageViewHolder.commentCount.setText("Reaction "+data.getComments());
        setUserImage(imageViewHolder,data);
        if (data.getPostVisibility().equalsIgnoreCase("1")){
            imageViewHolder.holder.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
    }


    private void setUserImage(ImageViewHolder holder, Posts data) {
        holder.userImg.setAdjustViewBounds(false);
        Glide.with(mContext)
                .load(data.getUser().getUserImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .into(holder.userImg);
    }

    private void setPostImage(ImageViewHolder holder, Posts data) {
        holder.postImage.setAdjustViewBounds(false);
        Glide.with(mContext)
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .dontAnimate()
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return null != messageList ? messageList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position) instanceof Posts){
            return (((Posts) messageList.get(position)).getViewType());
        }else {
            return Constant.VIEW_TYPE.NEW_POST;
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView userImg;
        private TextView userName;
        private TextView postCaption;
        private ImageView postImage;
        private TextView commentCount;
        private TextView likeCount;
        private CardView holder;
        public ImageViewHolder(View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.user_img);
            userName = itemView.findViewById(R.id.user_name);
            postCaption = itemView.findViewById(R.id.caption);
            postImage = itemView.findViewById(R.id.post_image);
            commentCount = itemView.findViewById(R.id.comments);
            holder = itemView.findViewById(R.id.holder);
        }
    }

    class NewPostViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        public NewPostViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
        }
    }


}
