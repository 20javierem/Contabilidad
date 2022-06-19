package org.moreno.validators;

import jakarta.validation.ConstraintViolation;
import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.utilities.Utilities;

import java.awt.*;
import java.util.Set;

public class CategoryValidator extends ProgramValidator {
    public static Set<ConstraintViolation<Category>> loadViolations(Category category) {
        Set<ConstraintViolation<Category>> violations = PROGRAMA_VALIDATOR.validate(category);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Category>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Category> error1= (ConstraintViolation<Category>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}