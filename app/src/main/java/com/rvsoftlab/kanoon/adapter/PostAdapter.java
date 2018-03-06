package com.rvsoftlab.kanoon.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.model.Posts;
import java.util.List;

/**
 * Created by RVishwakarma on 3/6/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ImageViewHolder>{

    private Context mContext;
    private List<Posts> mPost;
    public PostAdapter(Context context, List<Posts> list) {
        this.mContext = context;
        this.mPost = list;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_list_item_layout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Posts data = mPost.get(position);
        setPostImage(holder,data);
        holder.postCaption.setText(data.getCaption());
        holder.userName.setText(data.getUser().getUserName());
        holder.commentCount.setText("Reaction "+data.getComments());
        setUserImage(holder,data);
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
        return mPost.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView userImg;
        private TextView userName;
        private TextView postCaption;
        private ImageView postImage;
        private TextView commentCount;
        private TextView likeCount;
        public ImageViewHolder(View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.user_img);
            userName = itemView.findViewById(R.id.user_name);
            postCaption = itemView.findViewById(R.id.caption);
            postImage = itemView.findViewById(R.id.post_image);
            commentCount = itemView.findViewById(R.id.comments);
        }
    }


}
