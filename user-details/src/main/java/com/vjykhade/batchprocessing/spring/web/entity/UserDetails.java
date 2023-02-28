package com.vjykhade.batchprocessing.spring.web.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user_details")
public class UserDetails {
    @Id
    @Column(name="uid")
    private String id;
    private String fullName;
    private String birthDate;
    private String city;
    private String mobileNo;
    private String batchNo;

}
