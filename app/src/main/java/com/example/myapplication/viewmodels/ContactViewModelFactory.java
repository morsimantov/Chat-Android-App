package com.example.myapplication.viewmodels;


import android.content.Context;

import com.example.myapplication.api.ContactsAPI;

public class ContactViewModelFactory {

    static ContactViewModel contactViewModel;


    public static ContactViewModel getViewModelInstance(String username, Context context, String server) {
        // if there isn't a view model or there isn't an instance with the same username
        if (contactViewModel == null || username.equals(contactViewModel.getUsername()) == false) {
            contactViewModel = new ContactViewModel(username, context, server);
        }
        return contactViewModel;
    }
//    public static ContactViewModel getViewModelInstance(String username, Context context, ContactsAPI api) {
//        // if there isn't a view model or there isn't an instance with the same username
//        if (contactViewModel == null || username.equals(contactViewModel.getUsername()) == false) {
//            contactViewModel = new ContactViewModel(username, context, api);
//        }
//        return contactViewModel;
//    }
}
