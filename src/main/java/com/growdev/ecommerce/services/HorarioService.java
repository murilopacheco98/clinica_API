//package com.growdev.ecommerce.services;
//
//import com.growdev.ecommerce.entities.Horario;
//import com.growdev.ecommerce.exceptions.exception.BadRequestException;
//import com.growdev.ecommerce.exceptions.exception.InternalServerException;
//import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
//import com.growdev.ecommerce.repositories.HorarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//@Service
//public class HorarioService {
//    @Autowired
//    HorarioRepository horarioRepository;
//
//    @Transactional(readOnly = true)
//    public List<Horario> findAll() {
//        return horarioRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Horario> findAllPaged(PageRequest pageRequest) {
//        return horarioRepository.findAll(pageRequest);
//    }
//
//    @Transactional(readOnly = true)
//    public Horario findById(Long id) {
//        return horarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
//    }
//
//    public void delete(Long id) {
//        try {
//            horarioRepository.deleteById(id);
//        }//é do spring e serve para reclamar que nao executou nada no banco
//        catch (EmptyResultDataAccessException e) {
//            throw new ResourceNotFoundException("Not found " + id);
//        }//caso a categoria tenha dados vinculados a ela, não poderá ser excluida, nisso, apresentamos
//        catch (DataIntegrityViolationException e) {
//            throw new InternalServerException("Intregrity Violation");
//        }
//    }
//
//    public Horario create(Horario horario) {
//        return horarioRepository.save(horario);
//    }
//
//    public Horario checkAvailability(Horario horario) {
//        String yearNow = String.valueOf(LocalDate.now().getYear());
//        String monthNow = String.valueOf(LocalDate.now().getMonth());
//        String dayNow = String.valueOf(LocalDate.now().getDayOfMonth());
//
//        List<String> hours = new ArrayList<>(Arrays.asList("07:00:00",
//                "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00",
//                "10:00:00","10:30:00","11:00:00","13:00:00","13:30:00",
//                "14:00:00","14:30:00","15:00:00","15:30:00","16:00:00",
//                "16:30:00","17:00:00","17:30:00"));
//        List<String> days = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
//                "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
//                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"));
//        List<String> months = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
//                "07", "08", "09", "10", "11", "12"));
//        for (String month : months) {
//            if (Objects.equals(month, monthNow)) {
//                for (String day : days) {
//                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
//                        for (String hour : hours ) {
//                            System.out.println(hour);
//                        }
//                    }
//                }
//            }
//        }
////        try {
////            horarioRepository.save(horario);
////        } catch (Exception e) {
////            throw new BadRequestException("Não é possível salvar esse horario.");
////        }
//        return horario;
//    }
//
//    public Horario update(Horario horario, Long id) {
//        Horario horarioFound = horarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
//        try {
////            horarioFound.setHoraMinuto(horario.getHoraMinuto());
//            horarioRepository.save(horarioFound);
//        } catch (ResourceNotFoundException e) {
//            throw new BadRequestException("Os dados da requisição estão errados.");
//        }
//        return horarioFound;
//    }
//}
