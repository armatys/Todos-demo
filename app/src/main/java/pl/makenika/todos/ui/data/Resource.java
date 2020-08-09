package pl.makenika.todos.ui.data;

public abstract class Resource<T> {
    public static class Idle<T> extends Resource<T> {
    }

    public static class Loading<T> extends Resource<T> {
    }

    public static class Loaded<T> extends Resource<T> {
        public final T value;

        public Loaded(T value) {
            this.value = value;
        }
    }

    public static class Error<T> extends Resource<T> {
        public final Throwable throwable;

        public Error(Throwable throwable) {
            this.throwable = throwable;
        }
    }
}
