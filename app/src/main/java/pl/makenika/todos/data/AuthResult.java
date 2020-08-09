package pl.makenika.todos.data;

public class AuthResult {
    public final boolean isSuccessful;
    public final String message;

    public AuthResult(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }
}
