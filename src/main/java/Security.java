public class Security {
    public static String encryptPassword(String password) {
        int shift = 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            char encrypted = (char) (c + shift);
            result.append(encrypted);
        }
        return result.toString();
    }

    public static String decryptPassword(String encryptedPassword) {
        int shift = 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encryptedPassword.length(); i++) {
            char c = encryptedPassword.charAt(i);
            char decrypted = (char) (c - shift);
            result.append(decrypted);
        }
        return result.toString();
    }
}
