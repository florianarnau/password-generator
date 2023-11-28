package passwordgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * PasswordGenerator
 */
public class PasswordGenerator {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez la longueur du mot de passe : ");
        int length = scanner.nextInt();

        System.out.println("Voulez-vous inclure des lettres majuscules ? (Oui/Non) : ");
        boolean includeUpperCase = scanner.next().equalsIgnoreCase("Oui");

        System.out.println("Voulez-vous inclure des lettres minuscules ? (Oui/Non) : ");
        boolean includeLowerCase = scanner.next().equalsIgnoreCase("Oui");
    
        System.out.print("Voulez-vous inclure des chiffres ? (Oui/Non) : ");
        boolean includeDigits = scanner.next().equalsIgnoreCase("Oui");

        System.out.print("Voulez-vous inclure des caractères spéciaux ? (Oui/Non) : ");
        boolean includeSpecialChars = scanner.next().equalsIgnoreCase("Oui");
        
        String password = generatePassword(length, includeUpperCase, includeLowerCase, includeDigits, includeSpecialChars);

        System.out.println("Mot de passe généré : " + password);

        savePasswordToFile("- " + password);
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