package com.example.foodplannerapp.presentation.meals.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.databinding.ItemInstructionStepBinding;
import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.StepViewHolder> {
    private final DiffUtil.ItemCallback<String> diffCallback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<String> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<String> list) {
        differ.submitList(list);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(
                ItemInstructionStepBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String step = differ.getCurrentList().get(position);
        holder.binding.tvStepNumber.setText(String.valueOf(position + 1));

        holder.binding.tvStepText.setText(step);

        if (position == differ.getCurrentList().size() - 1) {
            holder.binding.viewLine.setVisibility(View.GONE);
        } else {
            holder.binding.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        final ItemInstructionStepBinding binding;

        public StepViewHolder(ItemInstructionStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}