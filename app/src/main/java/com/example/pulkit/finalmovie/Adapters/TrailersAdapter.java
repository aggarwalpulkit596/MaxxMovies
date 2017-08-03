package com.example.pulkit.finalmovie.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.pulkit.finalmovie.ConstantKey;
import com.example.pulkit.finalmovie.MovieDetailActivity;
import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.Model.Trailers;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.util.List;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.VideoInfoHolder> {
    private Context mContext;
    private List<Trailers> mTrailers;
    String[] VideoID = {"ePbKGoIGAXY", "2LqzF5WauAw", "nyc6RJEEe0U"};
    YoutubePlayClickListerner mListener;

    public TrailersAdapter(Context context, List<Trailers> mTrailers, YoutubePlayClickListerner listerner) {
        this.mContext = context;
        this.mTrailers = mTrailers;
        mListener = listerner;
    }

    public interface YoutubePlayClickListerner{
        void onPlayClick(String key);
    }

    @Override
    public TrailersAdapter.VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(ConstantKey.YOUTUBE_API, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(mTrailers.get(position).getKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mTrailers == null) ? 0 : mTrailers.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            mListener.onPlayClick(mTrailers.get(getLayoutPosition()).getKey());

        }
    }

}
