package com.example.myapplication.viewmodels;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.contacts.Contact;
import com.example.myapplication.messages.Message;
import com.example.myapplication.repositories.ContactsRepository;
import com.example.myapplication.repositories.MessagesRepository;

import java.util.List;

public class MessageViewModel {
    private LiveData<List<Message>> messages;
    private MessagesRepository repo;

    public MessageViewModel(int chatId, Context context, String username, String contactName, String server) {
        repo = new MessagesRepository(chatId, context, username, contactName, server);
        this.messages = repo.getAll();
    }

    public LiveData<List<Message>> getMessages() {
        return repo.getAll();
    }

    public void add(Message message) {
        repo.add(message);
    }

//    public void add(String id, Message message) {
//        repo.add(id, message);
//    }

//    public void refresh() {
//        this.repository.refresh();
//    }

//    public ContactsRepository getRepository() {
//        return repo;
//    }
}