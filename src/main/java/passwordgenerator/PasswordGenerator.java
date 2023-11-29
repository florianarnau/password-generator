package passwordgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * PasswordGenerator
 */
public class PasswordGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length;
        do {
            System.out.println("Entrez la longueur du mot de passe (minimum 8 caractères) : ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                length = 8; // Longueur par défaut si l'utilisateur appuie sur "Entrée".
                break;
            }

            try {
                length = Integer.parseInt(input);
                if (length < 8) {
                    System.out.println("La longueur du mot de passe doit être au minimum égale à 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                length = -1;
            }
        } while (length < 8);

        boolean includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars;

        do {
            includeUpperCase = getBooleanInput("Voulez-vous inclure des lettres majuscules ? (Oui/Non) : ", scanner);
            includeLowerCase = getBooleanInput("Voulez-vous inclure des lettres minuscules ? (Oui/Non) : ", scanner);
            includeDigits = getBooleanInput("Voulez-vous inclure des chiffres ? (Oui/Non) : ", scanner);
            includeSpecialChars = getBooleanInput("Voulez-vous inclure des caractères spéciaux ? (Oui/Non) : ", scanner);

            // Vérifie que l'utilisateur a répondu "Oui" à au moins une des options
            if (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars)) {
                System.out.println("Vous devez inclure au moins un type de caractère. Veuillez répondre par 'Oui' pour au moins une option.");
            }
        } while (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars));

        String password = generatePassword(length, includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars);

        System.out.println("Mot de passe généré : " + password);

        savePasswordToFile("- " + password);
    }

    private static boolean getBooleanInput(String message, Scanner scanner) {
        boolean result;
        String userInput;
        do {
            System.out.print(message);
            userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equals("oui")) {
                result = true;
            } else if (userInput.equals("non")) {
                result = false;
            } else {
                System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
                result = false;
            }
        } while (!userInput.equals("oui") && !userInput.equals("non"));
        return result;
    }


    private static void savePasswordToFile(String password) {
        try (FileWriter writer = new FileWriter("passwords.txt", true)) {
            writer.write(password + System.lineSeparator());
            System.out.println("Mot de passe enregistré dans le fichier 'passwords.txt'.");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du mot de passe dans le fichier.");
            e.printStackTrace();
        }
    }

    private static String generatePassword(int length, boolean includeUpperCase, boolean includeLowerCase, boolean includeDigits, boolean includeSpecialChars) {
        StringBuilder password = new StringBuilder();
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";

        if (!includeUpperCase && !includeLowerCase && !includeDigits && !includeSpecialChars) {
            System.out.println("Vous devez inclure au moins un type de caractère. Veuillez répondre par 'Oui' pour au moins une option.");
            return ""; // Retourne un mot de passe vide
        }

        String allChars = "";

        if (includeUpperCase){
            allChars += upperCaseChars;
        }

        if(includeLowerCase) {
            allChars += lowerCaseChars;
        }

        if(includeDigits) {
            allChars += digitChars;
        }

        if(includeSpecialChars) {
            allChars += specialChars;
        }

        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        return password.toString();
    }
}