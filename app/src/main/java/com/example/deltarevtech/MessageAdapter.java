package com.example.deltarevtech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) { // User Type
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_user, parent, false);
        } else { // AI Type
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_ai, parent, false);
        }
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.tvMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSentBy().equals(Message.SENT_BY_USER)) {
            return 0; // User
        } else {
            return 1; // AI
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        public MyViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                tvMessage = itemView.findViewById(R.id.tv_message_user);
            } else {
                tvMessage = itemView.findViewById(R.id.tv_message_ai);
            }
        }
    }
}