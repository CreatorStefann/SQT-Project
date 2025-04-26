package ro.ase.kebab.exceptions;

public class MissingProteinException extends RuntimeException {
    public MissingProteinException() {
        super("Kebab must have a protein");
    }
}
