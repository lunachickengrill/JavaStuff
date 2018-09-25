package com.genericinventory.domain.shared;

public final class GenericValidationRule {

	public static void isTrue(boolean expression, String errorMessageTemplate, Object... errorMessageArguments) {
		isTrue(expression, String.format(errorMessageTemplate, errorMessageArguments));
	}

	public static void isTrue(boolean expression, String errorMessage) {
		if (!expression) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void notEmpty(String string, String errorMessage) {
		if (string.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void notNull(Object reference, String errorMessage) {
		if (reference == null) {
			throw new NullPointerException(errorMessage);
		}
	}

}
