package org.moreno.validators;

import jakarta.validation.ConstraintViolation;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Utilities;

import java.awt.*;
import java.util.Set;

public class RecordValidator extends ProgramValidator {
    public static Set<ConstraintViolation<Record>> loadViolations(Record record) {
        Set<ConstraintViolation<Record>> violations = PROGRAMA_VALIDATOR.validate(record);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Record>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Record> error1= (ConstraintViolation<Record>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Utilities.sendNotification("Error", error, TrayIcon.MessageType.ERROR);
    }
}