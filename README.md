# Advanced programming Ex3 - Android
By: Mor Siman Tov and Shai Fisher

## Dependecies
Installations required:

- Android-Studio - https://developer.android.com/studio
- MariaDB - https://mariadb.org/download/
- .NET - https://dotnet.microsoft.com/en-us/download

## How to use the code
**Due to lack of time for testing, we did not manage to submit the server connection properly, so we decided to submit the local app that works properly,
and add to this repo the server API classes as well**.

Note that the contacts API did work properly but we didn't have enough time to check the other API requests. Furtheremore, we managed to sucsesfully connect firebase with a token, and see the received notifications (but because there is no connection to the server we didn't connect it to the current running project).

API project link:
https://github.com/ShaiFisher1/Advanced_Programming2_Ex2

Get the code of our project using the command git clone and copy the url.
Run the Android studio code and start using the chat app.

## About the app
Create a new user in the registration page (there is a button to register in the login page). 
Password must be at least 8 characters with letters and numbers. You can also add a profile picture of your own if you choose to. If not, a default profile picture will be presented.

After registeration you'll be directed to the login page, where you can sign in with your username and password.

After login the contacts screen will appear, where you can add new contacts using the button on the bottom right of the screen.

**Note that you can only add registered contacts (that are also users).**

**When a user adds a contact, the user will also be added as contact in the contact's chats and messages will be updated acorrdingly in both chats.**

Above the button that adds a contact (on the bottom right of the contacts screen) there is a button of settings. It will direct you to the settings screen where you can change the application from light mode to dark mode (and vice versa), and also change the server address (currently it a default address).

Start a new chat with a user in the app by clickinig on a contact. Now a chat with that contact will open up and you can send messages.

Every message will be shown with its date and time.
Start as many chats as you want. For each chat you can see the last message and when it was sent in the contacts screen.



## Notes
In our project, we created a chat app that uses Rooms Database.
The app works locally.
You can register with a new user and start chatting.
