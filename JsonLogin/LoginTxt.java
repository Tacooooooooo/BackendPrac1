package JsonLogin;

import java.util.Map;
import java.util.HashMap;

import java.io.*;
import java.util.Scanner;

public class LoginTxt {
    private static final String FILE_NAME = "accounts.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, String> accounts = loadAccounts();

        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter new ID: ");
                String newId = sc.nextLine();
                if (accounts.containsKey(newId)) {
                    System.out.println("ID already exists! Try another.");
                    continue;
                }
                System.out.print("Enter new Password: ");
                String newPassword = sc.nextLine();
                accounts.put(newId, newPassword);
                saveAccounts(accounts);
                System.out.println("Sign up successful!");
            } else if (choice == 2) {
                System.out.print("Enter ID: ");
                String loginId = sc.nextLine();
                System.out.print("Enter Password: ");
                String loginPassword = sc.nextLine();

                if (accounts.containsKey(loginId) && accounts.get(loginId).equals(loginPassword)) {
                    System.out.println("Logged in successfully!");
                } else {
                    System.out.println("Incorrect ID or Password.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option! Try again.");
            }
        }

        sc.close();
    }

    private static Map<String, String> loadAccounts() {
        Map<String, String> accounts = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":"); // format: ID:Password
                if (parts.length == 2) {
                    accounts.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            // File not found? No worries, just start empty
        } catch (IOException e) {
            System.out.println("Error reading accounts file.");
        }
        return accounts;
    }

    private static void saveAccounts(Map<String, String> accounts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : accounts.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts file.");
        }
    }
}
