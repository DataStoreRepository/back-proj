package com.core.util;

public class InputValidation {
    
    // Verifica se o email é válido
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    // Verifica se um campo não é nulo ou vazio
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Verifica se o nome tem um tamanho mínimo
    public static boolean isValidLength(String name, int minLength) {
        return name != null && name.length() >= minLength;
    }
}
