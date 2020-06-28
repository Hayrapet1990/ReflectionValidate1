package org.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static List<String> validate(Object object) throws IllegalAccessException {
        List<String> allErrors = new ArrayList<>();
        Class anyClass = object.getClass();
        Field[] childClassFields = anyClass.getDeclaredFields();
        Field[] parentClassFields = anyClass.getSuperclass().getDeclaredFields();
        List<String> firstError = checkExceptions(object, childClassFields);
        List<String> secondError = checkExceptions(object, parentClassFields);
        allErrors.addAll(firstError);
        allErrors.addAll(secondError);
        return allErrors;
    }

    public static List<String> checkExceptions(Object object, Field[] aneClass) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field field : aneClass) {
            field.setAccessible(true);
            if (field.getType().isPrimitive()) {
                if ((Integer) field.get(object) <= 0) {

                    try {
                        throw new PrimitiveZeroException("The primitive field " + field.getName()
                                + " " + field.getType().getSimpleName() + " has negative or zero value");
                    } catch (PrimitiveZeroException ex) {
                        errors.add(ex.toString());
                    }
                }
            } else {
                if (field.get(object) == null) {
                    try {
                        throw new InstanceNullException("The instance field " + field.getName()
                                + " " + field.getType().getSimpleName() + " has null value");
                    } catch (InstanceNullException ex) {
                        errors.add(ex.toString());
                    }
                }

            }
        }
        return errors;
    }


}
