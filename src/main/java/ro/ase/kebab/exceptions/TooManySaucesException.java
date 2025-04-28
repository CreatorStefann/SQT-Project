package ro.ase.kebab.exceptions;

public class TooManySaucesException extends RuntimeException {
    public TooManySaucesException() {
        super("Cannot add more than 3 sauces");
    }
}
