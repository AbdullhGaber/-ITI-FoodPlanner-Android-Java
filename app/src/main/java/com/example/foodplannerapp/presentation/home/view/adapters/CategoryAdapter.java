package com.example.foodplannerapp.presentation.home.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal_category.Category;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final DiffUtil.ItemCallback<Category> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

    private final AsyncListDiffer<Category> differ = new AsyncListDiffer<>(this, diffCallback);

    private final boolean isGrid;

    public CategoryAdapter() {
        this.isGrid = false;
    }

    public CategoryAdapter(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isGrid ? R.layout.item_category_grid : R.layout.item_category;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Category> list) {
        differ.submitList(list);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView tvCategoryName;
        Context context;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }

        public void bind(Category category) {
            tvCategoryName.setText(category.getName());

            Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                    .setDuration(1800)
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build();

            ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
            shimmerDrawable.setShimmer(shimmer);

            Glide.with(context)
                    .load(category.getImage())
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.ic_broken_image)
                    .into(imgCategory);
        }
    }
}