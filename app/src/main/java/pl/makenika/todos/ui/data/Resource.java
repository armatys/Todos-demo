package pl.makenika.todos.ui.data;

import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class Resource<T> {
    public static class Idle<T> extends Resource<T> {
        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof Idle<?>;
        }
    }

    public static class Loading<T> extends Resource<T> {
        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof Loading<?>;
        }
    }

    public static class Loaded<T> extends Resource<T> {
        public final T value;

        public Loaded(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Loaded<?> loaded = (Loaded<?>) o;
            return value.equals(loaded.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class Error<T> extends Resource<T> {
        public final Throwable throwable;

        public Error(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Error<?> error = (Error<?>) o;
            return throwable.equals(error.throwable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(throwable);
        }
    }
}
