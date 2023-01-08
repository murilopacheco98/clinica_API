package com.growdev.ecommerce.validation.user;

import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.dto.user.user.UserPacienteDTO;
import com.growdev.ecommerce.entities.user.Paciente;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.exceptions.FieldMessage;
import com.growdev.ecommerce.repositories.PacienteRepository;
import com.growdev.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

//A CLASSE IMPLEMENTA O CONSTRAINTVALIDATOR POIS HAVERÁ UMA ANNOTATION LIGADA A CLASSE E UMA CLASSE QUE RECEBERÁ A ANNOTATION
public class UserPacienteDTOValidationBean implements ConstraintValidator<UserPacienteDTOValidation, UserPacienteDTO> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void initialize(UserPacienteDTOValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(UserPacienteDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

//        UserEntity userEntity = userRepository.findByEmail(value.getUserInsertDTO().getEmail());
//        if (userEntity != null) {
//            list.add(new FieldMessage("email", "Este email já está cadastrado."));
//        }
//
//        Paciente paciente = pacienteRepository.findByCpf(value.getPacienteDTO().getCpf());
//        if (paciente != null) {
//            list.add(new FieldMessage("cpf", "Este CPF já está cadastrado."));
//        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
