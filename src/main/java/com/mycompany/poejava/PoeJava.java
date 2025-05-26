/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poejava;

import javax.swing.*; //part of Joptionpane
import javax.swing.JPasswordField; //Chatgpt assisted to musk/hide password 
import java.util.*;
import java.io.FileWriter;
import java.io.IOException; 
import org.json.simple.JSONArray; //chatgpt assisted
import org.json.simple.JSONObject; //chatgpt assisted


public class PoeJava {

    //main method
    public static void main(String[] args) {
       //(Open AI, 2025)ChatGPT assited with login feature [Accessed]:<https://openai.com/chatgpt/overview/>
        Login login = new Login();
        
// prompting user to sign up, login, or exit
        while (true) {
            String[] menuOptions = {"Sign Up", "Login", "Exit"};
            int menuChoice = JOptionPane.showOptionDialog(null, "Welcome! What would you like to do?",
                    "User System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, menuOptions, menuOptions[0]);

            if (menuChoice == 0) {
                login.signUpUser();
            } else if (menuChoice == 1) {
                login.promptLogin();
            } else {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break;
            }
        }
    }
 // this is the login class
    public static class Login {
        String userName;
        String passWord;
        String cellPNumber;
     //Array to store usernames, passwords, and cellphone numbers
        String[] savedUserNames = new String[5];
        String[] savedPasswords = new String[5];
        String[] savedCellNumbers = new String[5];
        int registeredUserCount = 0;

     //method to check if username is less than or equal to 5 characters
        public boolean checkUserName(String userName) {
            return userName.contains("_") && userName.length() <= 5;
        }
      // Password complexity check 
        public boolean checkPasswordComplexity(String passWord) {
            return passWord.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$");
        }
       // Checks if cell phone number is valid South African format
        public boolean checkCellPhoneNumber(String cellPNumber) {
            return cellPNumber.matches("^\\+27[6-8][0-9]{8}$");
        }
   // Checks if the username already exists in saved array
        public boolean isUserNameCaptured(String userName) {
            for (String existingUser : savedUserNames) {
                if (userName != null && userName.equals(existingUser)) return true;
            }
            return false;
        }
         // checks if the password already exists
        public boolean isPasswordCaptured(String passWord) {
            for (String existingPass : savedPasswords) {
                if (passWord != null && passWord.equals(existingPass)) return true;
            }
            return false;
        }
         // Check if the cellphone number already exists
        public boolean isCellPhoneCaptured(String cellPNumber) {
            for (String existingCell : savedCellNumbers) {
                if (cellPNumber != null && cellPNumber.equals(existingCell)) return true;
            }
            return false;
        }
           //method to sign up
        public void signUpUser() {
            if (registeredUserCount >= 5) {
                JOptionPane.showMessageDialog(null, "User limit reached. Please login instead.");
                return;
            }

            while (true) {
                userName = JOptionPane.showInputDialog("Enter a username (must contain _ and be no more than 5 characters):");
                if (userName == null) return;
                if (isUserNameCaptured(userName)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please login.");
                    loginExistingUser(userName);
                    return;
                } else if (checkUserName(userName)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username.");
                }
            }
                   //Chatgpt assisted with jpassword to musk it
            while (true) {
                JPasswordField passwordInputField = new JPasswordField();
                int passwordOption = JOptionPane.showConfirmDialog(null, passwordInputField,
                        "Enter your password:", JOptionPane.OK_CANCEL_OPTION);
                if (passwordOption == JOptionPane.OK_OPTION) {
                    passWord = new String(passwordInputField.getPassword());
                    if (isPasswordCaptured(passWord)) {
                        JOptionPane.showMessageDialog(null, "Password already exists. Please login.");
                        loginExistingUser(userName);
                        return;
                    } else if (checkPasswordComplexity(passWord)) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Password not correctly formatted.");
                    }
                } else {
                    return;
                }
            }

            while (true) {
                cellPNumber = JOptionPane.showInputDialog("Enter your cell phone number with country code (+27...):");
                if (cellPNumber == null) return;
                if (isCellPhoneCaptured(cellPNumber)) {
                    JOptionPane.showMessageDialog(null, "Cell phone already exists. Please login.");
                    loginExistingUser(userName);
                    return;
                } else if (checkCellPhoneNumber(cellPNumber)) {
                    JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                }
            }
                // method for registering the user called
            registerUser();
            JOptionPane.showMessageDialog(null, "Registration successful!");
            loginExistingUser(userName);
        }
              //method to register the user
        public void registerUser() {
            savedUserNames[registeredUserCount] = userName;
            savedPasswords[registeredUserCount] = passWord;
            savedCellNumbers[registeredUserCount] = cellPNumber;
            registeredUserCount++;
        }
 // this method prompts user to login
        public void promptLogin() {
            String inputUserName = JOptionPane.showInputDialog("Enter your username:");
            if (inputUserName == null) return;
            if (!isUserNameCaptured(inputUserName)) {
                JOptionPane.showMessageDialog(null, "Username not found.");
                return;
            }
            loginExistingUser(inputUserName);
        }
              
        public void loginExistingUser(String inputUserName) {
            int userIndex = -1;
            for (int i = 0; i < registeredUserCount; i++) {
                if (savedUserNames[i].equals(inputUserName)) {
                    userIndex = i;
                    break;
                }
            }

            if (userIndex == -1) {
                JOptionPane.showMessageDialog(null, "User not found.");
                return;
            }

            while (true) {
                JPasswordField loginPasswordField = new JPasswordField();
                int loginOption = JOptionPane.showConfirmDialog(null, loginPasswordField,
                        "Enter your password:", JOptionPane.OK_CANCEL_OPTION);
                if (loginOption == JOptionPane.OK_OPTION) {
                    String enteredPassword = new String(loginPasswordField.getPassword());
                    if (enteredPassword.equals(savedPasswords[userIndex])) {
                        JOptionPane.showMessageDialog(null, "Welcome, " + inputUserName + "! It is great to see you again.");
                        runQuickChat();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password.");
                    }
                } else {
                    break;
                }
            }
        }
    // --QUICKCHAT METHOD
        public void runQuickChat() {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

            int totalMessages = 0;
            try {
                String messageLimitStr = JOptionPane.showInputDialog("How many messages would you like to send?");
                totalMessages = Integer.parseInt(messageLimitStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Exiting QuickChat.");
                return;
            }
             //array that holds messages
            ArrayList<Message> sentMessages = new ArrayList<>();
            int messageCount = 0;

            while (true) {
                String[] options = {"Send Messages", "Show Recently Sent Messages", "Quit"};
                int choice = JOptionPane.showOptionDialog(null, "Choose an option:",
                        "QuickChat Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);

                if (choice == 0) {
                    if (messageCount >= totalMessages) {
                        JOptionPane.showMessageDialog(null, "You have reached the message limit.");
                        continue;
                    }
                       // Chatgot assisted
                    Message msg = new Message();
                    if (msg.composeMessage(messageCount + 1)) {
                        sentMessages.add(msg);
                        msg.storeMessage();
                        messageCount++;
                        JOptionPane.showMessageDialog(null, "Message successfully sent.");
                    }
                } else if (choice == 1) {
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                } else {
                    break;
                }
            }

            JOptionPane.showMessageDialog(null, "Total messages sent: " + messageCount);
        }
    }
         // Message class for message ID,number, recipient, text, and hash
    public static class Message {
        String messageID;
        int messageNumber;
        String recipient;
        String messageText;
        String messageHash;
           
        //method to compose the message
        public boolean composeMessage(int num) {
            messageNumber = num;

            // Message ID - chatgpt assisted
            messageID = String.format("%010d", new Random().nextInt(1000000000));
            JOptionPane.showMessageDialog(null, "Message ID generated: " + messageID);
            if (!checkMessageID()) {
                JOptionPane.showMessageDialog(null, "Message ID must be 10 digits.");
                return false;
            }

            // Recipient
            recipient = JOptionPane.showInputDialog("Enter recipient's number (start with + and 10 digits):");
            if (recipient == null || !checkRecipientCell()) {
                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
            }

            // Message Text
            messageText = JOptionPane.showInputDialog("Enter message text (max 250 characters):");
            if (messageText == null) return false;
            if (messageText.length() > 250) {
                int excess = messageText.length() - 250;
                JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excess + ", please reduce size.");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Message ready to send.");
            }

            // Generate message hash- chatgpt assisted
            messageHash = createMessageHash();
            JOptionPane.showMessageDialog(null, "Message Hash: " + messageHash);

            // promt user to Choose action
            String[] sendOptions = {"Send Message", "Disregard Message", "Store Message to Send Later"};
            int action = JOptionPane.showOptionDialog(null, "Choose what to do with the message:",
                    "Message Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, sendOptions, sendOptions[0]);

            if (action == 1) {
                JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                return false;
            } else if (action == 2) {
                JOptionPane.showMessageDialog(null, "Message successfully stored.");
            }

            showMessage();
            return true;
        }
          // method to check messageID- CHatgpt assisted
        public boolean checkMessageID() {
            return messageID.length() == 10;
        }
           // method for recipient cellnumber
        public boolean checkRecipientCell() {
            return recipient.startsWith("+") && recipient.length() <= 13;
        }
           // chatgpt assisted
        public String createMessageHash() {
            String[] words = messageText.split("\\s+");
            String firstWord = words.length > 0 ? words[0] : "";
            String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
            return (messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        }

        public void showMessage() {
            JOptionPane.showMessageDialog(null,
                    "Message ID: " + messageID +
                            "\nMessage Hash: " + messageHash +
                            "\nRecipient: " + recipient +
                            "\nMessage: " + messageText);
        }
           //method to store messages - chatgpt assisted
        public void storeMessage() {
            JSONObject obj = new JSONObject();
            obj.put("messageID", messageID);
            obj.put("messageNumber", messageNumber);
            obj.put("recipient", recipient);
            obj.put("message", messageText);
            obj.put("messageHash", messageHash);

            JSONArray messageList = new JSONArray();
            messageList.add(obj);

            try (FileWriter file = new FileWriter("messages.json", true)) {
                file.write(messageList.toJSONString() + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save message to file.");
            }
        }
    }
}

