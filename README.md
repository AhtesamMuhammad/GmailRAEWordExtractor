# Gmail Get RAE Definition in HTML
This Java project allows accessing the website of the [Real Academia Española (RAE)](https://dle.rae.es/) to obtain the definition of a specific word by sending emails and reading them from the inbox with the option to download the static HTML page of the searched word. It utilizes the HTTPS protocol to access the website and retrieve the definition.

## Features
#### Email Sending
Send emails using the user's Gmail account using an application key. The word whose definition is desired is specified in the email body.

#### Email Reception
Handles receiving emails using the IMAP protocol. When the email subject contains the word "RAE word", where "word" is the word whose definition is desired, the program extracts the definition of the word and displays it on the console.

#### Static Page Download
In addition to displaying the definition on the console, the program asks the user if they want to download the static page from the RAE containing the definition in HTML format.

## Requirements
To send and receive emails via Gmail, you will need a Gmail account with two-step authentication enabled. This enhanced security measure requires an additional layer of verification beyond your regular password. In this process, you will generate an [Application Password](https://support.google.com/accounts/answer/185833?hl=en), different from your usual login credentials. In this way, your account will be protected and you will be able to access your project's features.

#### Gmail Account with Two-Step Authentication
> **Steps to follow:**
> 1. Log in to your Gmail Account.
> 2. Access Security Settings.
> 3. Go to the Security section.
> 4. Enable Two-Step Verification.
> 5. Select the "App Passwords" Option.
> 6. Generate an application key.
> 7. Save the generated key.

#### IMAPS Activation to Read Emails
> **Steps to follow:**
> 1. Access Gmail Settings.
> 2. Navigate to "Forwarding and POP/IMAP".
> 3. Enable IMAP.
> 4. Save the changes.

### HttpClient 1.1
The project utilizes version 1.1 of the HttpClient library to access the RAE website. It is essential to ensure that this specific version is properly configured in your development environment. This ensures compatibility and seamless functionality with the project's codebase.


### Credentials
Emphasis should be placed on filling in the account data and the subject and content of credentials.txt.

> [!IMPORTANT]
> If you would like to get it to look up a definition of a word you have to start with "RAE" at the beginning and then the word

| Parameters  | Values |
| ------------- | ------------- |
| `email` | exemple@gmail.com  |
| `addressee`  | exemple@gmail.com  |
| `passwordApp`  | asdf asdf asdf asdf  |

### Exemples:
#### Exemple Sending Email #1:
**Input:**
```
Menu:
1. Send email
2. Receive email
0. Exit
Choose an option to perform: 1
Enter the subject of the email: RAE mariposa
Enter the content of the email: Create a program to practice sending e-mail using Java
```
**Result:**
```
Email sent to: addresseeXXX@gmail.com
```
#### Exemple Sending Email #2:
**Input:**
```
Menu:
1. Send email
2. Receive email
0. Exit
Choose an option to perform: 1
Enter the subject of the email: Labios
Enter the content of the email: Create a program to practice sending e-mail using Java
```
**Result:**
```
Email sent to: addresseeXXX@gmail.com
```
---
#### Exemple Receiving Email #1:
**Input:**
```
Menu:
1. Send email
2. Receive email
0. Exit
Choose an option to perform: 2
Choose the subject that you would like to read:
1. RAE mariposa
Enter the number corresponding to the desired email subject: 1

```
**Result:**
```
Sender by: [senderXXX@gmail.com]
Addressee: [addresseeXXX@gmail.com]
Date: Thu Apr 18 17:43:14 CEST 2024
Subject: RAE mariposa
Content: Create a program to practice sending e-mail using Java

Definition: 
mariposa: 1. f. Insecto de boca chupadora, con dos pares de alas cubiertas de escamas y generalmente de colores brillantes, que constituye la fase adulta de los lepidópteros.
Do you want to download the Definition in a HTML?
yes | no
yes
[ HTML content saved to C:\Users\..\..\mariposa.html ]
```

#### Exemple Receiving Email #2:
**Input:**
```
Menu:
1. Send email
2. Receive email
0. Exit
Choose an option to perform: 2
Choose the subject that you would like to read:
1. RAE mariposa
2. Labios
Enter the number corresponding to the desired email subject: 2
```
**Result:**
```
Sender by: [senderXXX@gmail.com]
Addressee: [addresseeXXX@gmail.com]
Date: Thu Apr 18 17:58:05 CEST 2024
Subject: Labios
Content: Create a program to practice sending e-mail using Java
```
