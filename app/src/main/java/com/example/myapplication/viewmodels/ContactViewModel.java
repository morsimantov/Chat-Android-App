package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Contact;
import com.example.myapplication.repositories.ContactsRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private LiveData<List<Contact>> contacts;
    private ContactsRepository repo;
    private String username;

    public ContactViewModel(String username, Context context, String server) {
        this.username = username;
        repo = new ContactsRepository(username, context, server);
        contacts = repo.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return repo.getAll();
    }
//
//    public List<Contact> getAll() {
//        return repo.getContacts();
//    }

    public Contact getContact(String contactid) {
        return repo.get(contactid);
    }

    public void add(Contact contact) {
        repo.add(contact);
    }

    public void updateContact(Contact contact) {
        repo.update(contact);
    }


    public void remove(Contact contact) {
        repo.delete(contact);
    }

//    public void refresh() {
//        this.repo.refresh();
//    }

    public ContactsRepository getRepository() {
        return repo;
    }


    public String getUsername() {
        return username;
    }
}
