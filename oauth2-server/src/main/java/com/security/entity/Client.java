package com.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author fanzhenxing
 * @create 2018/4/27 9:28 AM
 */
@Entity
@Table(name = "auth_client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String secret;
    private String name;
    private String locked = "0";
    private String description;
    private Date crtTime;
    private String crtUser;
    private String crtName;
    private String crtHost;
    private Date updTime;
    private String updUser;
    private String updName;
    private String updHost;

    @OneToMany(
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private List<ClientService> clientServices;

}
