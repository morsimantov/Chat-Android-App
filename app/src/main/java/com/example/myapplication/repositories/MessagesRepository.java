package com.example.myapplication.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.AppDB;
import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.models.Transfer;
import com.example.myapplication.models.Chat;
import com.example.myapplication.db.ChatDao;
import com.example.myapplication.models.Message;
import com.example.myapplication.db.MessageDao;
import com.example.myapplication.models.NewMessageObject;
import com.example.myapplication.Utils;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {
    private MessageDao messageDao;
    private MessageAPI messageAPI;
    private AppDB db;
    private int chatId;
    private ChatDao chatDao;
    private MessageListData messageListData;
    private String server;
    private String username;
    private String contactId;

    public MessagesRepository(int chatId, Context context, String username, String contactId, String server) {
        this.db = AppDB.getInstance(context);
        this.messageDao = db.messageDao();
        this.chatDao = db.chatDao();
        this.chatId = chatId;
        this.username = username;
        this.contactId = contactId;
        this.messageListData = new MessageListData();
        this.server = server;
        this.messageAPI = new MessageAPI(messageListData, messageDao, server);
        this.messageAPI.get(this, username, contactId);
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }

    public void add(Message message) {
        NewMessageObject newMsgObj = new NewMessageObject(this.username, message.content);
        messageAPI.transfer(this, contactId, new Transfer(username, contactId, message.content), newMsgObj);
    }

    public void handleAPIData(int status, List<Message> messages) {
        if (status == 200) {
            new Thread(() -> {
                messageDao.deleteAll();
                if (chatId != 0) {
                    for (Message message : messages) {
                        if (message.created != null) {
                            message.setCreated(Utils.formatDateTimeString(message.created));
                        }
                        messageDao.insert(message);
                    }
                } else {
                    int newChatId = 0;
                    for (Message message : messages) {
                        newChatId = message.chatId;
                        if (message.created != null) {
                            message.setCreated(Utils.formatDateTimeString(message.created));
                        }
                        messageDao.insert(message);
                    }
                    Chat currentChat = chatDao.getById(newChatId);
                    if (currentChat != null) {
                        chatDao.delete(currentChat);
                    }
                    if (newChatId != 0) {
                        chatDao.insert(new Chat(newChatId, username, contactId));
                        this.chatId = newChatId;
                    }

                }
            this.messageListData.postValue(messageDao.getByChatId(chatId));
            }).start();
        }
    }

    public void postHandle(int status) {
        if (status == 201) {
            new Thread(() -> {
                messageAPI.get(this, username, contactId);
            }).start();
        }
    }

    public void afterTransfer(String contactId, NewMessageObject message) {
        messageAPI.post(this, contactId, message);
    }

    class MessageListData extends MutableLiveData<List<Message>> {

        public MessageListData() {
            super();
            List<Message> messages = new LinkedList<Message>();
            setValue(messages);
        }

        class PrimeThread extends Thread {
            public void run() {
                messageListData.postValue(messageDao.getByChatId(chatId));
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
            PrimeThread t = new PrimeThread();
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}