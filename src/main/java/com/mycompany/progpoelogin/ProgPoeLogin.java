/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.progpoelogin;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField; //ChatGPT assisted 

public class ProgPoeLogin {

    public static void main(String[] args) { 
        //(Open AI, 2025)ChatGPT assited with login feature [Accessed]:<https://openai.com/chatgpt/overview/>
        Login login = new Login(); // Create an instance of the Login class

        while (true) {
            // Let user choose whether to Sign Up, Login, or Exit
            String[] menuOptions = {"Sign Up", "Login", "Exit"};
            int menuChoice = JOptionPane.showOptionDialog(null, "Welcome! What would you like to do?",
                    "User System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, menuOptions, menuOptions[0]);

            if (menuChoice == 0) {
                login.signUpUser(); // Call method to handle user sign-up
            } else if (menuChoice == 1) {
                login.promptLogin(); // Call method to handle login
            } else {
                JOptionPane.showMessageDialog(null, "Goodbye!"); // Exit option
                break;
            }
        }
    }

    public static class Login {
        // User input variables
        String userName;
        String passWord;
        String cellPNumber;

        // Arrays to store user details (maximum 5 users)
        String[] savedUserNames = new String[5];
        String[] savedPasswords = new String[5];
        String[] savedCellNumbers = new String[5];
        int registeredUserCount = 0; // Tracks how many users have registered

        // Check if username is valid (contains underscore and max 5 characters)
        public boolean checkUserName(String userName) {
            return userName.contains("_") && userName.length() <= 5;
        }

        // Password complexity check (removed logic here to allow any password)
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

        // Checks if the password already exists
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

        // Method to handle user registration
        public void signUpUser() {
            // Check if user limit is reached
            if (registeredUserCount >= 5) {
                JOptionPane.showMessageDialog(null, "User limit reached. Please login instead.");
                return;
            }

            // Get and validate username
            while (true) {
                userName = JOptionPane.showInputDialog("Enter a username (must contain _ and be no more than 5 characters):");
                if (userName == null) return;
                if (isUserNameCaptured(userName)) {
                    JOptionPane.showMessageDialog(null, "Username already captured. Please login.");
                    loginExistingUser(userName);
                    return;
                } else if (checkUserName(userName)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username. It must contain an underscore and be no more than 5 characters.");
                }
            }

            // Get and validate password (with hidden input)
            while (true) {
                JPasswordField passwordInputField = new JPasswordField();
                int passwordOption = JOptionPane.showConfirmDialog(null, passwordInputField,
                        "Enter your password:", JOptionPane.OK_CANCEL_OPTION);
                if (passwordOption == JOptionPane.OK_OPTION) {
                    passWord = new String(passwordInputField.getPassword());
                    if (isPasswordCaptured(passWord)) {
                        JOptionPane.showMessageDialog(null, "Password already captured. Please login.");
                        loginExistingUser(userName);
                        return;
                    } else if (checkPasswordComplexity(passWord)) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Password not correctly formatted.");
                    }
                } else {
                    return; // User cancelled input
                }
            }

            // Get and validate cell phone number
            while (true) {
                cellPNumber = JOptionPane.showInputDialog("Enter your cell phone number with country code (+27...):");
                if (cellPNumber == null) return;
                if (isCellPhoneCaptured(cellPNumber)) {
                    JOptionPane.showMessageDialog(null, "Cell phone number already captured. Please login.");
                    loginExistingUser(userName);
                    return;
                } else if (checkCellPhoneNumber(cellPNumber)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid cell phone number.");
                }
            }

            // Save user details
            registerUser();
            JOptionPane.showMessageDialog(null, "Registration successful!");
            loginExistingUser(userName); // Log in immediately after successful registration
        }

        // Save the current user input into the user arrays
        public void registerUser() {
            savedUserNames[registeredUserCount] = userName;
            savedPasswords[registeredUserCount] = passWord;
            savedCellNumbers[registeredUserCount] = cellPNumber;
            registeredUserCount++;
        }

        // Start login process by asking for username
        public void promptLogin() {
            String inputUserName = JOptionPane.showInputDialog("Enter your username:");
            if (inputUserName == null) return;
            if (!isUserNameCaptured(inputUserName)) {
                JOptionPane.showMessageDialog(null, "Username not found. Please sign up first.");
                return;
            }
            loginExistingUser(inputUserName);
        }

        // Actual login method using password for matched username
        public void loginExistingUser(String inputUserName) {
            int userIndex = -1;
            // Find index of the entered username
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

            // Ask for password input until correct one is entered
            while (true) {
                JPasswordField loginPasswordField = new JPasswordField();
                int loginOption = JOptionPane.showConfirmDialog(null, loginPasswordField,
                        "Enter your password:", JOptionPane.OK_CANCEL_OPTION);
                if (loginOption == JOptionPane.OK_OPTION) {
                    String enteredPassword = new String(loginPasswordField.getPassword());
                    if (enteredPassword.equals(savedPasswords[userIndex])) {
                        JOptionPane.showMessageDialog(null, "Welcome, " + inputUserName + "! It is great to see you again.");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
                    }
                } else {
                    break; // User cancelled password input
                }
            }
        }
    }
}
