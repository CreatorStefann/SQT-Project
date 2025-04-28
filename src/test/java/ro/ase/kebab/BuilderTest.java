package ro.ase.kebab;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import ro.ase.kebab.builder.KebabBuilder;
import ro.ase.kebab.exceptions.InvalidIngredientException;
import ro.ase.kebab.exceptions.MissingProteinException;
import ro.ase.kebab.exceptions.TooManySaucesException;
import ro.ase.kebab.factory.IngredientFactory;
import ro.ase.kebab.model.Kebab;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {

    @Test
    void shouldCreateKebabSuccessfully() {
        Try<Kebab> kebabTry = new KebabBuilder()
                .withProtein(IngredientFactory.createIngredient("chicken").get())
                .withCarbohydrate(IngredientFactory.createIngredient("rice").get())
                .addPickle(IngredientFactory.createIngredient("cucumbers").get())
                .addWrap(IngredientFactory.createIngredient("salad").get())
                .addSauce(IngredientFactory.createIngredient("tahini").get())
                .addSauce(IngredientFactory.createIngredient("ketchup").get())
                .build();

        assertTrue(kebabTry.isSuccess(), "Kebab should be created successfully");
        assertNotNull(kebabTry.get().getProtein());
        assertNotNull(kebabTry.get().getCarbohydrate());
        assertEquals(2, kebabTry.get().getSauces().size());
    }

    @Test
    void shouldFailWhenProteinMissing() {
        Try<Kebab> kebabTry = new KebabBuilder()
                .withCarbohydrate(IngredientFactory.createIngredient("potatoes").get())
                .addPickle(IngredientFactory.createIngredient("onions").get())
                .build();

        assertTrue(kebabTry.isFailure(), "Kebab creation should fail without protein");
        assertTrue(kebabTry.getCause() instanceof MissingProteinException, "Failure should be MissingProteinException");
    }

    @Test
    void shouldFailWhenAddingTooManySauces() {
        Try<Kebab> kebabTry = Try.of(() -> (Kebab) new KebabBuilder()
                .withProtein(IngredientFactory.createIngredient("falafel").get())
                .withCarbohydrate(IngredientFactory.createIngredient("rice").get())
                .addSauce(IngredientFactory.createIngredient("tahini").get())
                .addSauce(IngredientFactory.createIngredient("ketchup").get())
                .addSauce(IngredientFactory.createIngredient("samurai").get())
                .addSauce(IngredientFactory.createIngredient("tzatziki").get())
                .build());

        assertTrue(kebabTry.isFailure(), "Kebab creation should fail with too many sauces");
        assertTrue(kebabTry.getCause() instanceof TooManySaucesException, "Failure should be TooManySaucesException");
    }

    @Test
    void shouldFailWhenAddingSauceBeforeProtein() {
        Try<KebabBuilder> builderTry = Try.of(() -> new KebabBuilder()
                .addSauce(IngredientFactory.createIngredient("ketchup").get()));

        assertTrue(builderTry.isFailure(), "Adding sauce before protein should fail");
        assertTrue(builderTry.getCause() instanceof IllegalStateException, "Failure should be IllegalStateException");
    }

    @Test
    void shouldFailWhenCreatingUnknownIngredient() {
        Try<Void> ingredientTry = IngredientFactory.createIngredient("unknown-sauce-name")
                .map(i -> null); // just testing creation

        assertTrue(ingredientTry.isFailure(), "Creating an unknown ingredient should fail");
        assertTrue(ingredientTry.getCause() instanceof InvalidIngredientException, "Failure should be InvalidIngredientException");
    }
}
