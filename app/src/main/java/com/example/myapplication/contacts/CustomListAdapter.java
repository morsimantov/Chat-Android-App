package com.example.myapplication.contacts;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;

    public CustomListAdapter(Context ctx, ArrayList<Contact> contactArrayList) {
        super(ctx, R.layout.custom_list_item, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        if (convertView == null) {
            // creates a view
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        // insert to convert view all the required fields
        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.contactNameItem);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);

        // set last text and last date
        if (contact.getLast() != null) {
            lastMsg.setText(contact.getLast());
            time.setText(contact.getLastdate());
        }

        // set profile picture
        if (!contact.getProfilePic().equals("")) {
            byte[] decodedByte = Base64.decode(contact.getProfilePic(), 0);
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
            imageView.setImageBitmap(bitmap);
        } else {
            Uri imageUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/drawable/profile_pic");
            imageView.setImageURI(null);
            imageView.setImageURI(imageUri);
        }

        userName.setText(contact.getId());

        return convertView;
    }
}