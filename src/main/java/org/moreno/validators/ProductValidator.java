package org.moreno.validators;

import jakarta.validation.ConstraintViolation;
import org.moreno.models.Product;
import org.moreno.utilities.Utilities;

import java.awt.*;
import java.util.Set;

public class ProductValidator extends ProgramValidator {
    public static Set<ConstraintViolation<Product>> loadViolations(Product product) {
        Set<ConstraintViolation<Product>> violations = PROGRAMA_VALIDATOR.validate(product);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Product>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Product> error1= (ConstraintViolation<Product>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}
