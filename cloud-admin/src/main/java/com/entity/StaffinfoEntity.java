package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Data
@Entity
@Table(name = "hd_staffinfo")
public class StaffinfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String description;
    private String status;
    private String phone;
    private String telephone;
    private String sex;
    private String identity;
    private String email;
    private String jobNo;
    private String address;
    private Date birthday;
    private Date lastSignDate;



    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "hd_staffinfo_role",joinColumns = {@JoinColumn(name = "staffinfo_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id" ,referencedColumnName = "id")})
    private Set<RoleEntity> roles;


}
