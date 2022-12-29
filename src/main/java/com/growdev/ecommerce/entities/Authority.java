package com.growdev.ecommerce.entities;

import com.growdev.ecommerce.entities.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
