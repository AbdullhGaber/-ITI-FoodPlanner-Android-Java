package com.example.foodplannerapp.presentation.planner.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.databinding.ItemCalendarDayBinding;
import com.example.foodplannerapp.presentation.model.CalendarDateModel;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final DiffUtil.ItemCallback<CalendarDateModel> diffCallback = new DiffUtil.ItemCallback<CalendarDateModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull CalendarDateModel oldItem, @NonNull CalendarDateModel newItem) {
            return oldItem.getFullDate().getTime() == newItem.getFullDate().getTime();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CalendarDateModel oldItem, @NonNull CalendarDateModel newItem) {
            return oldItem.getDayNumber().equals(newItem.getDayNumber()) &&
                    oldItem.getDayOfWeek().equals(newItem.getDayOfWeek()) &&
                    oldItem.isSelected() == newItem.isSelected();
        }
    };
    private final AsyncListDiffer<CalendarDateModel> differ = new AsyncListDiffer<>(this, diffCallback);

    private int selectedPosition = 0;
    private final OnDateClickListener listener;

    public interface OnDateClickListener {
        void onDateClick(CalendarDateModel dateModel);
    }

    public CalendarAdapter(OnDateClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<CalendarDateModel> dates) {
        if (!dates.isEmpty()) {
            dates.get(selectedPosition).setSelected(true);
        }
        differ.submitList(dates);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCalendarDayBinding binding = ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CalendarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarDateModel dateModel = differ.getCurrentList().get(position);
        holder.bind(dateModel, position);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        private final ItemCalendarDayBinding binding;

        public CalendarViewHolder(ItemCalendarDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CalendarDateModel dateModel, int position) {
            binding.tvDayOfWeek.setText(dateModel.getDayOfWeek());
            binding.tvDayNumber.setText(dateModel.getDayNumber());

            if (dateModel.isSelected()) {
                binding.cardViewDay.setCardBackgroundColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.green_header));
                binding.tvDayOfWeek.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.white));
                binding.tvDayNumber.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.white));
            } else {
                binding.cardViewDay.setCardBackgroundColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.creamy));
                binding.tvDayOfWeek.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.olive_grey));
                binding.tvDayNumber.setTextColor(
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.dark_charcoal));
            }

            binding.getRoot().setOnClickListener(v -> {
                if (position == RecyclerView.NO_POSITION || position == selectedPosition) {
                    return;
                }

                differ.getCurrentList().get(selectedPosition).setSelected(false);
                notifyItemChanged(selectedPosition);

                selectedPosition = position;
                differ.getCurrentList().get(selectedPosition).setSelected(true);
                notifyItemChanged(selectedPosition);

                listener.onDateClick(dateModel);
            });
        }
    }
}