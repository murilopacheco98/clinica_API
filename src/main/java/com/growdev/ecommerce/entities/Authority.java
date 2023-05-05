package com.growdev.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_authority")
public class Authority implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String authority;

//  @ManyToMany(mappedBy = "authority")
//  private Set<UserEntity> users = new HashSet<>();

}
