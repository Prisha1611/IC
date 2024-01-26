package com.example.marapixels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// StoriesAdapter.java
    public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {
        private List<StoryModel> stories;

        // Constructor to initialize the list of stories
        public StoriesAdapter(List<StoryModel> stories) {
            this.stories = stories;
        }

        // ViewHolder class
        public static class StoryViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView;
            TextView contentTextView;

            public StoryViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.storyTitleTextView);
                contentTextView = itemView.findViewById(R.id.storyContentTextView);
            }
        }

        // Create the ViewHolder
        @NonNull
        @Override
        public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
            return new StoryViewHolder(view);
        }

        // Bind data to the ViewHolder
        @Override
        public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
            StoryModel story = stories.get(position);
            holder.titleTextView.setText(story.getTitle());
            holder.contentTextView.setText(story.getContent());
        }

        // Get the total number of items
        @Override
        public int getItemCount() {
            return stories.size();
        }
    }


