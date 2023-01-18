//package com.growdev.ecommerce.entities;
//
//import lombok.*;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@Entity
//@Table(name = "horario")
//public class Horario implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String titulo;
//
//    @NotNull
//    private LocalDateTime inicio;
//
//    @NotNull
//    private LocalDateTime fim;
//
////    @Column(name = "hora_minuto", unique = true)
////    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
////    private LocalTime horaMinuto;
//}