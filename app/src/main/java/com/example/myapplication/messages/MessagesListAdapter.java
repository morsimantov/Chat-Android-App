package com.example.myapplication.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter {
    LayoutInflater inflater;
    public List<Message> messages;
    private static final int leftView = 0;
    private static final int rightView = 1;

    public MessagesListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == leftView) {
            View itemView = inflater.inflate(R.layout.right_message, parent, false);
            return new RightViewHolder(itemView);
        } else if (viewType == rightView) {
            View itemView = inflater.inflate(R.layout.left_message, parent, false);
            return new LeftViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            if (current.sent == true) {
                ((LeftViewHolder) holder).bind(current, position);
            } else  {
                ((RightViewHolder) holder).bind(current, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (messages == null) {
            return 0;
        } else {
            return messages.size();
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void updateMessages(List<Message> messageList) {
        messages = messageList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.sent == true) {
            return rightView;
        } else {
            return leftView;
        }
    }


    class LeftViewHolder extends RecyclerView.ViewHolder {

        private TextView messageContent;
        private TextView messageTime;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.leftMessageContent);
            messageTime = itemView.findViewById(R.id.leftMessageTime);
        }
        void bind(Message message, int position) {
            messageContent.setText(message.getContent());
            messageTime.setText(message.created);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {

        private TextView messageContent;
        private TextView messageTime;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.rightMessageContent);
            messageTime = itemView.findViewById(R.id.rightMessageTime);
        }
        void bind(Message message, int position) {
            messageContent.setText(message.getContent());
            messageTime.setText(message.created);
        }
    }
}