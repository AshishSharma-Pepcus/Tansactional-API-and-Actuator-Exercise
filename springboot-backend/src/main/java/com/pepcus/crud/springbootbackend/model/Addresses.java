package com.pepcus.crud.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
@Data
@Scope("prototype")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(name = "street")
    @NotNull
    @NotBlank
    private String street;

    @Column(name = "city")
    @NotNull
    @NotBlank
    private String city;

    @Column(name = "state")
    @NotNull
    @NotBlank
    private String state;

    @Column(name = "country")
    @NotNull
    @NotBlank
    private String country;
}
