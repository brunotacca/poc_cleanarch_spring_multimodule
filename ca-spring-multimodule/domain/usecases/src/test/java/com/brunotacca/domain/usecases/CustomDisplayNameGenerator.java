package com.brunotacca.domain.usecases;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

// Adapted from https://www.baeldung.com/junit-custom-display-name-generator
public class CustomDisplayNameGenerator {

  public static class ReplaceCamelCase extends DisplayNameGenerator.ReplaceUnderscores {
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return replaceCamelCase(super.generateDisplayNameForClass(testClass));
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return this.replaceCamelCase(testMethod.getName())
                  + ((testMethod.getParameterTypes()==null || testMethod.getParameterTypes().length==0)?"":DisplayNameGenerator.parameterTypesAsString(testMethod));
    }

    String replaceCamelCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        result.append(camelCase.charAt(0));
        for (int i=1; i<camelCase.length(); i++) {
            if (Character.isUpperCase(camelCase.charAt(i))) {
                result.append(' ');
                result.append(Character.toLowerCase(camelCase.charAt(i)));
            } else {
                result.append(camelCase.charAt(i));
            }
        }
        return result.toString();
    }
  }

  public static class IndicativeSentences extends ReplaceCamelCase {
    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return super.generateDisplayNameForNestedClass(nestedClass) + "...";
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return replaceCamelCase(testClass.getSimpleName() + " " + testMethod.getName()) + ".";
    }
  }

}