package fmi.java.web.tourFinder.validator;

public class Validation<E, T> {
    private final E errors;
    private final T value;

    private Validation(E errors, T value) {
        this.errors = errors;
        this.value = value;
    }

    public E getErrors() {
        return errors;
    }
    public static <E,T> Validation<E,T> valid(T value) {
        return new Validation<E,T>(null, value);
    }
    public static <E,T> Validation<E,T> invalid(E errors) {
        return new Validation<E,T>(errors, null);
    }

    public boolean isValid() {
        return errors == null;
    }

    public T getValue() {
        return value;
    }
}
