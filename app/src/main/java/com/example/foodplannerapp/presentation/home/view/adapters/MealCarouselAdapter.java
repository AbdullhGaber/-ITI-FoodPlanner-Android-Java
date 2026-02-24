package com.example.foodplannerapp.presentation.home.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meal.Meal;
import com.example.foodplannerapp.presentation.utils.ShimmerUtil;
import java.util.List;

import lombok.Setter;

public class MealCarouselAdapter extends RecyclerView.Adapter<MealCarouselAdapter.CarouselViewHolder> {

    // 1. Add the click listener interface
    public interface OnCarouselClickListener {
        void onMealClick(Meal meal);
    }

    @Setter
    private OnCarouselClickListener listener;

    private final DiffUtil.ItemCallback<Meal> diffCallback = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.getStrMeal().equals(newItem.getStrMeal()) &&
                    oldItem.getStrMealThumb().equals(newItem.getStrMealThumb());
        }
    };

    private final AsyncListDiffer<Meal> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<Meal> meals) {
        differ.submitList(meals);
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Meal meal = differ.getCurrentList().get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }
    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_meals_slide);
            name = itemView.findViewById(R.id.tv_meals_slide_name);
        }

        private void bind(Meal meal) {
            name.setText(meal.getStrMeal());
            ShimmerUtil.addShimmerToImage(itemView.getContext(), meal.getStrMealThumb(), image);
            itemView.setOnClickListener(v -> listener.onMealClick(meal));
        }
    }
}