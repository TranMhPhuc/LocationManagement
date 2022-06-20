package com.learn.locationmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.ItemLocationBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.common.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter
        extends ListAdapter<FavoriteLocation, LocationAdapter.LocationViewHolder>
        implements Filterable {
    public static final DiffUtil.ItemCallback<FavoriteLocation> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteLocation>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteLocation oldItem, @NonNull FavoriteLocation newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteLocation oldItem, @NonNull FavoriteLocation newItem) {
            return oldItem.equals(newItem);
        }
    };

    private OnClickListener listener;
    private List<FavoriteLocation> originalFavoriteLocationList;
    private List<FavoriteLocation> filteredFavoriteLocationList;
    private final Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (filteredFavoriteLocationList == null) {
                filteredFavoriteLocationList = new ArrayList<>();
            }
            filteredFavoriteLocationList.clear();
            if (charSequence == null || charSequence.length() == 0) {
                filteredFavoriteLocationList.addAll(originalFavoriteLocationList);
            } else {
                String filterNameOrCode = charSequence.toString().toLowerCase().trim();
                for (FavoriteLocation favoriteLocation : originalFavoriteLocationList) {
                    if (favoriteLocation.getName().toLowerCase().contains(filterNameOrCode)
                            || favoriteLocation.getCode().toLowerCase().contains(filterNameOrCode)) {
                        filteredFavoriteLocationList.add(favoriteLocation);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredFavoriteLocationList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
            if (filterResults.values instanceof List) {
                List<FavoriteLocation> filterList = new ArrayList<>((List) filterResults.values);
                LocationAdapter.super.submitList(filterList);
            }
        }
    };

    public LocationAdapter(OnClickListener onClickListener) {
        super(DIFF_CALLBACK);
        this.listener = onClickListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(
                ItemLocationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void submitList(@Nullable List<FavoriteLocation> list) {
        originalFavoriteLocationList = list;
        super.submitList(list);
    }

    @Override
    public Filter getFilter() {
        return locationFilter;
    }

    public interface OnClickListener {
        void onItemClick(FavoriteLocation favoriteLocation);

        void onDirectionButtonClick(FavoriteLocation favoriteLocation);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private ItemLocationBinding binding;
        private Context itemContext;
        private ImageLoader imageLoader;

        public LocationViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            setControl(binding);
            setEvent();
        }

        private void setControl(@NonNull ItemLocationBinding binding) {
            this.binding = binding;
            itemContext = binding.getRoot().getContext();
            imageLoader = new ImageLoader(itemContext);
        }

        private void setEvent() {
            binding.getRoot().setOnClickListener(view -> {
                listener.onItemClick(getItem(getAdapterPosition()));
            });

            binding.btnDirection.setOnClickListener(view -> {
                listener.onDirectionButtonClick(getItem(getAdapterPosition()));
            });
        }

        public void bind(@NonNull FavoriteLocation favoriteLocation) {
            // TODO show info on the item: id, name and image with glide
            binding.tvLocationCodeLabel.setText(itemContext.getString(R.string.label_locationItem_code, favoriteLocation.getCode()));
            binding.tvLocationName.setText(itemContext.getString(R.string.label_locationItem_name, favoriteLocation.getName()));
            imageLoader.loadImageWithContext(favoriteLocation.getImage(), R.drawable.place, R.drawable.place, binding.ivLocationImage);
        }
    }
}
