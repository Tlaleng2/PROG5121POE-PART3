/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog5121poe2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class QuickChat {

    private final Scanner scanner;

    // Message collections
    private final List<message> sentMessages;
    private final List<message> disregardedMessages;
    private final List<message> storedMessages;

    // Parallel lists
    private final List<String> messageIDs;
    private final List<String> messageHashes;

    private int messageCounter;

    private static final Path FILE_PATH = Path.of("messages.json");

    // Constructor
    public QuickChat(Scanner scanner) {

        this.scanner = scanner;

        sentMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();

        messageIDs = new ArrayList<>();
        messageHashes = new ArrayList<>();

        messageCounter = 0;

        loadMessages();
    }

    // Main QuickChat menu
    public void startQuickChat() {

        System.out.println("\n===== QUICKCHAT =====");
        System.out.println("Welcome to QuickChat!");

        boolean running = true;

        while (running) {

            System.out.println("\nChoose an option:");
            System.out.println("1. Send messages");
            System.out.println("2. View sent messages");
            System.out.println("3. Search for a message");
            System.out.println("4. Delete a message");
            System.out.println("5. Display message report");
            System.out.println("6. Display Longest Message");
            System.out.println("7. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    sendMessages();
                    break;

                case "2":
                    viewSentMessages();
                    break;

                case "3":
                    searchMessage();
                    break;

                case "4":
                    deleteMessage();
                    break;

                case "5":
                    displayMessageReport();
                    break;

                case "6":
                    displayLongestMessage();
                    break;

                case "7":
                    System.out.println("Thank you for using QuickChat!");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose from 1 to 7.");
            }
        }
    }

    // Send, discard or store messages
    private void sendMessages() {

        System.out.print("How many messages would you like to send? ");

        int numberOfMessages;

        try {

            numberOfMessages = Integer.parseInt(scanner.nextLine());

            if (numberOfMessages <= 0) {

                System.out.println(
                        "Please enter a number greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid whole number.");
            return;
        }

        for (int i = 0; i < numberOfMessages; i++) {

            System.out.println("\nMessage " + (i + 1));

            System.out.print(
                    "Enter recipient cellphone number (+27...): ");

            String recipient = scanner.nextLine().trim();

            if (!recipient.matches("^\\+27[6-8][0-9]{8}$")) {

                System.out.println(
                        "Invalid cellphone number. Message not processed.");
                continue;
            }

            System.out.print("Enter your message: ");

            String messageText = scanner.nextLine();

            if (messageText.trim().isEmpty()
                    || messageText.length() > 250) {

                System.out.println(
                        "Message must contain 1 to 250 characters.");
                continue;
            }

            // Create a new message
            message newMessage = new message(
                    ++messageCounter,
                    "QuickChat User",
                    recipient,
                    messageText
            );

            System.out.println("\nChoose an action:");
            System.out.println("1. Send message");
            System.out.println("2. Discard message");
            System.out.println("3. Store message");

            System.out.print("Your choice: ");

            String action = scanner.nextLine().trim();

            switch (action) {

                case "1":

                    sentMessages.add(newMessage);

                    messageIDs.add(newMessage.getMessageID());
                    messageHashes.add(newMessage.getMessageHash());

                    System.out.println("\nMessage sent successfully!");
                    System.out.println(newMessage.printMessage());

                    saveMessage(newMessage);
                    break;

                case "2":

                    disregardedMessages.add(newMessage);

                    System.out.println("Message discarded.");
                    break;

                case "3":

                    storedMessages.add(newMessage);

                    System.out.println("Message stored successfully.");
                    System.out.println(newMessage.printMessage());
                    break;

                default:

                    System.out.println(
                            "Invalid choice. Message was not processed.");
                    break;
            }
        }
    }

    // Display sent messages
    private void viewSentMessages() {

        if (sentMessages.isEmpty()) {

            System.out.println("No sent messages available.");
            return;
        }

        System.out.println("\n===== SENT MESSAGES =====");

        for (message currentMessage : sentMessages) {

            System.out.println("--------------------");
            System.out.println(currentMessage.printMessage());
        }

        System.out.println("--------------------");

        System.out.println(
                "Total sent messages: " + sentMessages.size());
    }

    // Save sent messages
    private void saveMessage(message currentMessage) {

        updateMessageFile();

        System.out.println("Message saved to messages.json.");
    }

    // Search messages
    private void searchMessage() {

        System.out.println("\n===== SEARCH MESSAGES =====");
        System.out.println("1. Search by Message ID");
        System.out.println("2. Search by Recipient Number");
        System.out.print("Choose: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {

            case "1":
                searchByMessageID();
                break;

            case "2":
                searchByRecipient();
                break;

            default:
                System.out.println("Invalid search option.");
        }
    }

    // Search using Message ID
    private void searchByMessageID() {

        System.out.print("Enter Message ID: ");
        String searchID = scanner.nextLine().trim();

        for (message currentMessage : sentMessages) {

            if (currentMessage.getMessageID()
                    .equalsIgnoreCase(searchID)) {

                System.out.println("\nMessage found!");
                System.out.println(currentMessage.printMessage());
                return;
            }
        }

        System.out.println("Message not found.");
    }

    // Search using recipient cellphone number
    private void searchByRecipient() {

        System.out.print("Enter recipient cellphone number: ");
        String searchRecipient = scanner.nextLine().trim();

        boolean found = false;

        for (message currentMessage : sentMessages) {

            if (currentMessage.getRecipient()
                    .equals(searchRecipient)) {

                System.out.println("\nMessage found!");
                System.out.println(currentMessage.printMessage());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }

    // Delete message by hash
    private void deleteMessage() {

        System.out.println("\n===== DELETE MESSAGE =====");
        System.out.print("Enter the message hash to delete: ");

        String hashToDelete = scanner.nextLine().trim();
        boolean deleted = false;

        for (int i = 0; i < sentMessages.size(); i++) {

            message currentMessage = sentMessages.get(i);

            if (currentMessage.getMessageHash()
                    .equalsIgnoreCase(hashToDelete)) {

                sentMessages.remove(i);
                messageIDs.remove(currentMessage.getMessageID());
                messageHashes.remove(currentMessage.getMessageHash());

                updateMessageFile();

                System.out.println("Message successfully deleted.");
                deleted = true;
                break;
            }
        }

        if (!deleted) {
            System.out.println("Message hash not found.");
        }
    }

    // Save messages in JSON format
    private void updateMessageFile() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(sentMessages);

        try {

            Files.writeString(
                    FILE_PATH,
                    json,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            System.out.println("JSON file updated successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving JSON file: " + e.getMessage());
        }
    }

    // Load messages from JSON file
    private void loadMessages() {

        if (!Files.exists(FILE_PATH)) {

            System.out.println("No previous JSON messages found.");
            return;
        }

        try {

            Gson gson = new Gson();

            Type messageListType =
                    new TypeToken<List<message>>() {}.getType();

            String json = Files.readString(FILE_PATH);

            List<message> loadedMessages =
                    gson.fromJson(json, messageListType);

            if (loadedMessages == null) {
                return;
            }

            sentMessages.clear();
            messageIDs.clear();
            messageHashes.clear();

            for (message loadedMessage : loadedMessages) {

                sentMessages.add(loadedMessage);

                messageIDs.add(loadedMessage.getMessageID());
                messageHashes.add(loadedMessage.getMessageHash());

                messageCounter = Math.max(
                        messageCounter,
                        loadedMessage.getMessageNumber()
                );
            }

            System.out.println(
                    "Messages loaded successfully: "
                    + sentMessages.size());

        } catch (IOException | JsonSyntaxException e) {

            System.out.println(
                    "Error loading JSON messages: " + e.getMessage());
        }
    }

    // Display longest message
    private void displayLongestMessage() {

        System.out.println("\n===== LONGEST MESSAGE =====");

        if (sentMessages.isEmpty()) {

            System.out.println("No sent messages available.");
            return;
        }

        message longestMessage = sentMessages.get(0);

        for (message currentMessage : sentMessages) {

            if (currentMessage.getMessageText().length()
                    > longestMessage.getMessageText().length()) {

                longestMessage = currentMessage;
            }
        }

        System.out.println("Longest message:");
        System.out.println(longestMessage.printMessage());

        System.out.println("Character count: "
                + longestMessage.getMessageText().length());
    }

    // Display message report
    private void displayMessageReport() {

        System.out.println("\n========== MESSAGE REPORT ==========");

        if (sentMessages.isEmpty()) {

            System.out.println("No sent messages available.");
            System.out.println("Total sent messages: 0");
            return;
        }

        for (message currentMessage : sentMessages) {

            System.out.println("------------------------------------");

            System.out.println("Message ID: "
                    + currentMessage.getMessageID());

            System.out.println("Recipient: "
                    + currentMessage.getRecipient());

            System.out.println("Message: "
                    + currentMessage.getMessageText());

            System.out.println("Message Hash: "
                    + currentMessage.getMessageHash());
        }

        System.out.println("------------------------------------");

        System.out.println(
                "Total sent messages: " + sentMessages.size());

        System.out.println("====================================");
    }
}
