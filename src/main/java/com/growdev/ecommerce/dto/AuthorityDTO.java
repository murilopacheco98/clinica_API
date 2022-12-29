//package com.growdev.ecommerce.dto;
//
//import com.growdev.ecommerce.dto.user.UserDTO;
//import com.growdev.ecommerce.entities.Authority;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.validation.constraints.NotBlank;
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class AuthorityDTO {
//  private Long id;
//
//  @NotBlank(message = "O campo é obrigatório")
//  private String authority;
//
//  private Set<UserDTO> users = new HashSet<>();
//
//  public AuthorityDTO(Authority authority) {
//    this.id = authority.getId();
//    this.authority = authority.getAuthority();
//    authority.getUsers().forEach(user -> this.users.add(new UserDTO(user)));
//  }
//}
