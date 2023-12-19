package com.example.myapplication.contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Contact;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {
    LayoutInflater inflater;
    public List<Contact> contacts;
    private ContactsOnClickListener contactsListener;
    private Context context;

    public ContactsListAdapter(Context context, ContactsOnClickListener contactsListener) {
        this.inflater = LayoutInflater.from(context);
        this.contactsListener = contactsListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_item, parent, false);
        return new ContactViewHolder(view);
    }


    public void updateContacts(List<Contact> contactsList) {
        contacts = contactsList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact contact = contacts.get(position);
            // set last text and last date
            if (contact.getLast() != null) {
                holder.lastMsg.setText(contact.getLast());
                holder.time.setText(contact.getLastdate());
            }
            // set profile picture
            if (!contact.getProfilePic().equals("")) {
                byte[] decodedByte = Base64.decode(contact.getProfilePic(), 0);
                Bitmap bitmap = BitmapFactory
                        .decodeByteArray(decodedByte, 0, decodedByte.length);
                holder.imageView.setImageBitmap(bitmap);
            } else {
                Uri imageUri = Uri.parse("android.resource://" + this.context.getPackageName() + "/drawable/profile_pic");
                holder.imageView.setImageURI(null);
                holder.imageView.setImageURI(imageUri);
            }
            holder.userName.setText(contact.getId());
        }
    }

    @Override
    public int getItemCount() {
        if (contacts == null) {
            return 0;
        } else {
            return contacts.size();
        }
    }


    public List<Contact> getContacts() {
        return contacts;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView userName;
        private TextView lastMsg;
        private TextView time;

        private ContactViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.contactNameItem);
            lastMsg = itemView.findViewById(R.id.last_massage);
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            contactsListener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface ContactsOnClickListener {
        void onClick(View v, int position);
    }
}
