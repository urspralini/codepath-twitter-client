package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TweetItemViewHolder;
import com.codepath.apps.twitter.models.Tweet;

import java.util.List;

/**
 * Created by pbabu on 8/4/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Tweet> mTweets;
    private final Context mContext;

    public TweetsAdapter(Context mContext, List<Tweet> mTweets) {
        this.mContext = mContext;
        this.mTweets = mTweets;
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tweet_list_item, parent, false);
        return new TweetItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        TweetItemViewHolder tweetItemViewHolder = (TweetItemViewHolder)holder;
        tweetItemViewHolder.tvTweetText.setText(tweet.getBody());
        tweetItemViewHolder.tvUserHandle.setText("@"+tweet.getUser().getScreenName());
        Glide.with(mContext)
                .load(tweet.getUser().getProfileImageUrl())
                .fitCenter()
                .into(tweetItemViewHolder.ivUserProfileImage);

    }

    public void addAll(List<Tweet> tweets){
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clear(){
        mTweets.clear();
        notifyDataSetChanged();
    }
}
