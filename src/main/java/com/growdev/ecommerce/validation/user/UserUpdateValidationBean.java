package com.growdev.ecommerce.validation.user;

import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.exceptions.FieldMessage;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//A CLASSE IMPLEMENTA O CONSTRAINTVALIDATOR POIS HAVERÁ UMA ANNOTATION LIGADA A CLASSE E UMA CLASSE QUE RECEBERÁ A ANNOTATION
public class UserUpdateValidationBean implements ConstraintValidator<UserUpdateValidation, UserDTO> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserUpdateValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        UserEntity usuario = userRepository.findById(value.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        if (!Objects.equals(usuario.getEmail(), value.getEmail())) {
            UserEntity usuarioEmail = userRepository.findByEmail(value.getEmail());
            if (usuarioEmail != null) {
                list.add(new FieldMessage("email", "Esse email já existe"));
            }
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
