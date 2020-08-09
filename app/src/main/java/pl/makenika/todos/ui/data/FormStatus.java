package pl.makenika.todos.ui.data;

public abstract class FormStatus {
    public static class Idle extends FormStatus {
    }

    public static class Sending extends FormStatus {
    }

    public static class Success extends FormStatus {
        public final String message;

        public Success(String message) {
            this.message = message;
        }
    }

    public static class Error extends FormStatus {
        public final String message;

        public Error(String message) {
            this.message = message;
        }
    }
}
