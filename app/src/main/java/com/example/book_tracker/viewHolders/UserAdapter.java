package com.example.book_tracker.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_tracker.database.entities.User;

import com.example.book_tracker.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private final OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDeleteClick(User user);
    }

    public UserAdapter(OnDeleteClickListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.userNameTextView.setText(user.getUsername());

        holder.binding.deleteUserButton.setOnClickListener(v -> {
            listener.onDeleteClick(user);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding binding;

        public UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}