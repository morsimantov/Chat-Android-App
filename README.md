# Android Chat App
By: Mor Siman Tov and Shai Fisher

1. [About](#About)
2. [Overview](#Overview)
3. [Dependencies](#Dependencies)
4. [Instructions](#Instructions)

## About

This is an Android Chat App we created in Java. All you need is to register and login with your username, and then start chatting!
The app contains several screens, including registration screen, login screen, contacts screen and chatting screen.


We created a RESTful API server using MVC pattern in ASP.NET. This is the link to the server side's code: https://github.com/morsimantov/Chat-App-API-Server

The app uses Rooms and has a local DB that synchronizes with the server anytime the app is in use.

In this project we also used Firebase Push Notifications. If you are connected to the app, once another user sends you a message, You'll get a message notification straight to your device.

## Overview

Create a new user in the registration page (there is a button to register in the login page). 
Password must be at least 8 characters with letters and numbers. You can also add a profile picture of your own if you choose to. If not, a default profile picture will be presented.

![צילום מסך 2023-12-20 195231](https://github.com/morsimantov/Chat-Android-App/assets/92635551/415b7deb-3d1d-44e2-bbf6-f27ee82b9c5d)

After registration you'll be directed to the login page, where you can sign in with your username and password.

![צילום מסך 2023-12-20 195318](https://github.com/morsimantov/Chat-Android-App/assets/92635551/8db6ef4c-0267-4cdc-9a7f-0077c93cdda1)

After login the contacts screen will appear, where you can add new contacts using the button on the bottom right of the screen.

![צילום מסך 2023-12-20 194906](https://github.com/morsimantov/Chat-Android-App/assets/92635551/9770c676-59fd-40b0-a626-a9c74d233ab7)

**Note that you can only add registered contacts (that are also users).**

**When a user adds a contact, the user will also be added as contact in the contact's chats and messages will be updated accordingly in both chats.**

Start a new chat with a user in the app by clicking on a contact. Now a chat with that contact will open up and you can send messages.

![צילום מסך 2023-12-20 201357](https://github.com/morsimantov/Chat-Android-App/assets/92635551/cc40c0cb-525b-401b-8e90-e06e1da8ab97)

Every message will be shown with its date and time.
Start as many chats as you want. For each chat you can see the last message and when it was sent in the contacts screen.


In the contacts screen, below the button that adds a contact (on the bottom right of the contacts screen) there is a button of settings. It will direct you to the settings screen where you can change the application from light mode to dark mode (and vice versa), and also change the server address (currently it's a default address).

![צילום מסך 2023-12-20 195616](https://github.com/morsimantov/Chat-Android-App/assets/92635551/0b4ca4c0-a9b6-4741-8d7d-eeebfd74d766)

After turning to dark mode:

![צילום מסך 2023-12-20 195520](https://github.com/morsimantov/Chat-Android-App/assets/92635551/91f9a226-7e91-44f3-8e60-56bf82c87ec5)

![צילום מסך 2023-12-20 195042](https://github.com/morsimantov/Chat-Android-App/assets/92635551/98810a55-6aac-47f4-bc1e-c2c2c9de2cc8)



## Dependencies

Installations required:

- Android-Studio - https://developer.android.com/studio
- MariaDB - https://mariadb.org/download/
- .NET - https://dotnet.microsoft.com/en-us/download

## Instructions

API project link:
https://github.com/morsimantov/Chat-App-Web-API

Get the code of our project using the command git clone and copy the url.
Run the Android studio code and start using the chat app.

