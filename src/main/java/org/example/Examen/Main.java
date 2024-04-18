package org.example.Examen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static EmailSender emailSender;
    private static EmailReceive emailReceive;
    private static Map<String, String> credentials;

    public static void main(String[] args) {
        credentials = credentialReader();
        String email = credentials.get("email"); // We are using the same email for send and receive
        String password = credentials.get("passwordApp");
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String smtpPortRecieve = "993";
        String smtpProtocol = "imaps"; // We are using the protocol IMAPS

        emailSender = new EmailSender(email, password, smtpHost, smtpPort);
        emailReceive = new EmailReceive(email, password, smtpHost, smtpPortRecieve, smtpProtocol);

        menu();
    }

    private static void options() {
        System.out.println("Menu:");
        System.out.println("1. Send email");
        System.out.println("2. Receive email");
        System.out.println("0. Exit");
        System.out.print("Choose an option to perform: ");
    }

    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            options();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter the subject of the email: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter the content of the email: ");
                    String content = scanner.nextLine();
                    emailSender.sendEmail(credentials, subject, content);
                    break;
                case 2:
                    emailReceive.receiveEmail();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
            System.out.println("-------------------------");
        }
    }

    private static Map<String, String> credentialReader() {
        Map<String, String> credentials = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("credentials.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not founded: " + e.getMessage());
        }
        return credentials;
    }
}
