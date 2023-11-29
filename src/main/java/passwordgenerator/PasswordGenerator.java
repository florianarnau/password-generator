package passwordgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * PasswordGenerator
 */
public class PasswordGenerator {

    private static final ResourceBundle MESSAGES_FR = ResourceBundle.getBundle("Messages_fr");
    private static final ResourceBundle MESSAGES_EN = ResourceBundle.getBundle("Messages_en");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String languageChoice = getLanguageChoice(scanner);

        int length;
        do {
            System.out.println(getMessage("enter_password_length", languageChoice));
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                length = 8;
                break;
            }

            try {
                length = Integer.parseInt(input);
                if (length < 8) {
                    System.out.println(getMessage("password_length_error", languageChoice));
                }
            } catch (NumberFormatException e) {
                System.out.println(getMessage("invalid_number", languageChoice));
                length = -1;
            }
        } while (length < 8);

        boolean includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars;

        do {
            includeUpperCase = getBooleanInput("include_uppercase", scanner, languageChoice);
            includeLowerCase = getBooleanInput("include_lowercase", scanner, languageChoice);
            includeDigits = getBooleanInput("include_digits", scanner, languageChoice);
            includeSpecialChars = getBooleanInput("include_special_chars", scanner, languageChoice);

            if (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars)) {
                System.out.println(getMessage("include_at_least_one_character", languageChoice));
            }
        } while (!(includeUpperCase || includeLowerCase || includeDigits || includeSpecialChars));

        String password = generatePassword(length, includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars, languageChoice);

        System.out.println(getMessage("password_generated", languageChoice) + password);

        savePasswordToFile("- " + password, languageChoice);
    }

    private static boolean getBooleanInput(String messageKey, Scanner scanner, String language) {
        boolean result;
        String userInput;
        do {
            System.out.print(getMessage(messageKey, language));
            userInput = scanner.nextLine().trim().toLowerCase();
    
            if (language.equals("fr")) {
                result = userInput.equals("oui");
            } else { // Default to English
                result = userInput.equals("yes");
            }
    
            if (!(result || userInput.equals("non") || userInput.equals("no"))) {
                System.out.println(getMessage("yes_no_response_required", language));
            }
    
        } while (!(result || userInput.equals("non") || userInput.equals("no")));
        return result;
    }
    

    private static String getLanguageChoice(Scanner scanner) {
        String languageChoice;
        do {
            System.out.println(getMessage("choose_language", "en"));
            languageChoice = scanner.nextLine().trim().toLowerCase();
    
            if (!(languageChoice.equals("english") || languageChoice.equals("french") || languageChoice.equals("fr"))) {
                System.out.println(getMessage("invalid_language_choice", "en"));
            }
        } while (!(languageChoice.equals("english") || languageChoice.equals("french") || languageChoice.equals("fr")));
        return languageChoice.equals("french") ? "fr" : "en";
    }    

    private static void savePasswordToFile(String password, String languageChoice) {
        try (FileWriter writer = new FileWriter("passwords.txt", true)) {
            writer.write(password + System.lineSeparator());

            System.out.println(getMessage("password_saved", languageChoice));
        } catch (IOException e) {
            System.out.println(getMessage("error_saving_password", languageChoice));
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
            System.out.println(getMessage("include_at_least_one_character", languageChoice));
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

    private static String getMessage(String key, String language) {
        ResourceBundle messages = (language.equals("fr")) ? MESSAGES_FR : MESSAGES_EN;
        return messages.getString(key);
    }
}
