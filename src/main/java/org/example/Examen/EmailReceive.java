package org.example.Examen;

import javax.mail.*;
import java.io.IOException;
import java.util.*;

public class EmailReceive {

    private final String email;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;
    private final String smtpProtocol;

    public EmailReceive(String email, String password, String smtpHost, String smtpPort, String smtpProtocol) {
        this.email = email;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpProtocol = smtpProtocol;
    }

    public void receiveEmail() {
        Properties props = new Properties();
        props.setProperty("mail.imap.host", smtpHost);
        props.setProperty("mail.imap.port", smtpPort);
        props.setProperty("mail.imap.auth", "true");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.store.protocol", smtpProtocol);

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            try (Store store = session.getStore("imap")) {
                store.connect(email, password);
                Folder inbox = store.getFolder("Inbox");
                inbox.open(Folder.READ_ONLY);
                Message[] messages = inbox.getMessages();
                if (messages.length == 0) {
                    System.out.println("The Folder Inbox is empty");
                } else {
                    int chosenIndex = chooseSubjectIndex(messages);
                    if (chosenIndex != -1) {
                        processMessage(messages[chosenIndex]);
                    }
                }
                inbox.close();
            }
        } catch (MessagingException e) {
            System.err.println("Error receiving the email: " + e.getMessage());
        }
    }

    private int chooseSubjectIndex(Message[] messages) throws MessagingException {
        Scanner scanner = new Scanner(System.in);
        int numMessages = messages.length;

        System.out.println("Choose the subject that you would like to read:");
        for (int i = 0; i < numMessages; i++) {
            System.out.println((i + 1) + ". " + messages[i].getSubject());
        }

        int chosenIndex;
        do {
            System.out.print("Enter the number corresponding to the desired email subject: ");
            chosenIndex = scanner.nextInt() - 1;
            if (chosenIndex < 0 || chosenIndex >= numMessages) {
                System.out.println("Invalid input. Please enter a number between 1 and " + numMessages);
            }
        } while (chosenIndex < 0 || chosenIndex >= numMessages);

        return chosenIndex;
    }

    private void processMessage(Message message) {
        try {
            String subject = message.getSubject().toUpperCase();
            if (subject.startsWith("RAE")) {
                String word = extractWordFromSubject(subject);
                readMessage(subject, message);
                System.out.println("Definition: ");
                processDataRAE(word.toLowerCase());
            } else {
                readMessage(subject, message);
            }
        } catch (MessagingException | IOException e) {
            System.err.println("Error processing the message: " + e.getMessage());
        }
    }

    private static void readMessage(String subject, Message message) throws MessagingException, IOException {
        Address[] from = message.getFrom();
        Address[] to = message.getRecipients(Message.RecipientType.TO);
        Date date = message.getSentDate();
        String content = message.getContent().toString();

        System.out.println("Sender by: " + Arrays.toString(from));
        System.out.println("Addressee: " + Arrays.toString(to));
        System.out.println("Date: " + date);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
    }

    private void processDataRAE(String word) {
        Scanner scanner = new Scanner(System.in);
        String htmlDefinitionRAE = DefinitionWordRAE.getDefinition(word);
        if (htmlDefinitionRAE == null) {
            return;
        }
        System.out.println("Do you want to download the Definition in a HTML?");
        System.out.println("yes | no");

        String option;
        do {
            option = scanner.next().toLowerCase(Locale.ROOT);
            if (!option.equals("yes") && !option.equals("no")) {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        } while (!option.equals("yes") && !option.equals("no"));

        if (option.equals("yes")) {
            try {
                DefinitionWordRAE.saveHTMLToFile(htmlDefinitionRAE, word);
            } catch (IOException e) {
                System.err.println("Error saving HTML to file: " + e.getMessage());
            }
        }
    }

    private static String extractWordFromSubject(String subject) {
        // Assuming the subject is formatted like "RAE palabra"
        return subject.substring(4).trim(); // Remove "RAE " and trim any extra spaces
    }
}
