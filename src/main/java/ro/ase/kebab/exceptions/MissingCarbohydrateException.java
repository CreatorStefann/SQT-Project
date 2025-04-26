package ro.ase.kebab.exceptions;

public class MissingCarbohydrateException extends RuntimeException {
    public MissingCarbohydrateException() {
        super("Kebab must have a carbohydrate");
    }
}
