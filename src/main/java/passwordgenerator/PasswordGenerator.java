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

        String languageChoice = getLanguageChoice(scanner);

        int length;
        do {
            System.out.println((languageChoice.equals("fr")) ? "Entrez la longueur du mot de passe (minimum 8 caractères) : " : "Enter the password length (minimum 8 characters): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                length = 8;
                break;
            }

            try {
                length = Integer.parseInt(input);
                if (length < 8) {
                    System.out.println((languageChoice.equals("fr")) ? "La longueur du mot de passe doit être au minimum égale à 8." : "The password length must be at least 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println((languageChoice.equals("fr")) ? "Veuillez entrer un nombre valide." : "Please enter a valid number.");
                length = -1;
            }
        } while (length < 8);

        boolean includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars;

        do {
            includeUpperCase = getBooleanInput((languageChoice.equals("fr")) ? "Voulez-vous inclure des lettres majuscules ? (Oui/Non) : " : "Include uppercase letters? (Yes/No): ", scanner, languageChoice);
            includeLowerCase = getBooleanInput((languageChoice.equals("fr")) ? "Voulez-vous inclure des lettres minuscules ? (Oui/Non) : " : "Include lowercase letters? (Yes/No): ", scanner, languageChoice);
            includeDigits = getBooleanInput((languageChoice.equals("fr")) ? "Voulez-vous inclure des chiffres ? (Oui/Non) : " : "Include digits? (Yes/No): ", scanner, languageChoice);
            includeSpecialChars = getBooleanInput((languageChoice.equals("fr")) ? "Voulez-vous inclure des caractères spéciaux ? (Oui/Non) : " : "Include special characters? (Yes/No): ", scanner, languageChoice);

            if (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars)) {
                System.out.println((languageChoice.equals("fr")) ? "Vous devez inclure au moins un type de caractère. Veuillez répondre par 'Oui' pour au moins une option." : "You must include at least one type of character. Please answer 'Yes' for at least one option.");
            }
        } while (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars));

        String password = generatePassword(length, includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars, languageChoice);

        System.out.println((languageChoice.equals("fr")) ? "Mot de passe généré : " + password : "Generated password: " + password);

        savePasswordToFile("- " + password, languageChoice);
    }

    private static boolean getBooleanInput(String message, Scanner scanner, String language) {
        boolean result;
        String userInput;
        do {
            System.out.print(message);
            userInput = scanner.nextLine().trim().toLowerCase();

            if (language.equals("fr")) {
                if (userInput.equals("oui")) {
                    result = true;
                } else if (userInput.equals("non")) {
                    result = false;
                } else {
                    System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
                    result = false;
                }
            } else { // Default to English
                if (userInput.equals("yes")) {
                    result = true;
                } else if (userInput.equals("no")) {
                    result = false;
                } else {
                    System.out.println("Please answer with 'Yes' or 'No'.");
                    result = false;
                }
            }
        } while (!(userInput.equals("yes") || userInput.equals("no") || userInput.equals("oui") || userInput.equals("non")));
        return result;
    }

    private static String getLanguageChoice(Scanner scanner) {
        String languageChoice;
        do {
            System.out.println("Choose a language (English/French): ");
            languageChoice = scanner.nextLine().trim().toLowerCase();

            if (!(languageChoice.equals("english") || languageChoice.equals("french") || languageChoice.equals("fr"))) {
                System.out.println("Please choose 'English' or 'French'.");
            }
        } while (!(languageChoice.equals("english") || languageChoice.equals("french") || languageChoice.equals("fr")));
        return languageChoice.equals("french") ? "fr" : "en";
    }


    private static void savePasswordToFile(String password, String languageChoice) {
        try (FileWriter writer = new FileWriter("passwords.txt", true)) {
            writer.write(password + System.lineSeparator());
            
            String successMessage = (languageChoice.equals("fr")) ?
                "Mot de passe enregistré dans le fichier 'passwords.txt'." :
                "Password saved to 'passwords.txt' file.";
                
            System.out.println(successMessage);
        } catch (IOException e) {
            String errorMessage = (languageChoice.equals("fr")) ?
                "Erreur lors de l'enregistrement du mot de passe dans le fichier." :
                "Error saving password to file.";
                
            System.out.println(errorMessage);
            e.printStackTrace();
        }
    }

    private static String generatePassword(int length, boolean includeUpperCase, boolean includeLowerCase, boolean includeDigits, boolean includeSpecialChars, String languageChoice) {
        StringBuilder password = new StringBuilder();
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";

        if (!includeUpperCase && !includeLowerCase && !includeDigits && !includeSpecialChars) {
            String message = (languageChoice.equals("fr")) ?
                "Vous devez inclure au moins un type de caractère. Veuillez répondre par 'Oui' pour au moins une option." :
                "You must include at least one type of character. Please answer 'Yes' for at least one option.";
            
            System.out.println(message);
            return "";
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