package org.springframework.dwarf.player;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

	@Override
	public void initialize(PasswordConstraint password) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.length() > 5 && value.length() < 50;
	}
}
