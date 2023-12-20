package com.example.myapplication.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Utils;
import com.example.myapplication.models.Invitation;
import com.example.myapplication.db.AppDB;
import com.example.myapplication.api.ContactsAPI;
import com.example.myapplication.models.Contact;
import com.example.myapplication.db.ContactDao;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private ContactDao contactDao;
    private ContactsAPI contactsAPI;
    private AppDB db;
    private String username;
    private ContactListData contactListData;
    private String server;

    public ContactsRepository(String username, Context context, String server) {
        this.db = AppDB.getInstance(context);
        this.contactDao = db.contactDao();
        this.username = username;
        // MutableLiveData contacts list
        this.contactListData = new ContactListData();
        this.server = server;
        this.contactsAPI = new ContactsAPI(contactListData, contactDao, server);
        this.contactsAPI.get(this, username);
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public Contact get(String contactid) {
        return contactDao.get(contactid, username);
    }

    public void add(Contact contact) {
        this.contactListData.postValue(contactDao.index(username));
        contactsAPI.inviteContact(this, new Invitation(contact.getUsername(), contact.getId(), contact.getServer()), contact);
    }

    public void update(Contact contact) {
        contactDao.update(contact);
        this.contactListData.postValue(contactDao.index(username));
    }

    public void delete(Contact contact) {
        contactDao.delete(contact);
        this.contactListData.postValue(contactDao.index(username));
    }

    public void handleAPIData(int status, List<Contact> contacts) {
        if (status == 200) {
            new Thread(() -> {
                contactDao.deleteAll(username);
                for (Contact contact : contacts) {
                    contact.setUsername(username);
                    contact.setProfilePic("");
                    if (contact.getLastdate() != null) {
                        contact.setLastdate(Utils.formatDateTimeString(contact.getLastdate()));
                    }
                    System.out.println("created: " + contact.getLastdate());
                    contactDao.insert(contact);
                }
                contactListData.postValue(contacts);
            }).start();
        }
    }

    public void postHandle(int status, Contact contact) {
        if (status == 201) {
            new Thread(() -> {
                if (contact.getLastdate() != null) {
                    contact.setLastdate(Utils.formatDateTimeString(contact.getLastdate()));
                }
                contactDao.insert(contact);
                contactListData.postValue(contactDao.index(username));
            }).start();
        }
    }

    public void afterInvite(Contact contact) {
        contact.setUsername(username);
        contactsAPI.post(this, contact);
    }

    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            List<Contact> contacts = new LinkedList<Contact>();
            setValue(contacts);
        }

        class PrimeThread extends Thread {
            public void run() {
                contactListData.postValue(contactDao.index(username));
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