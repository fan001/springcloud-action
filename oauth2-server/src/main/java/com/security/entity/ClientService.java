package com.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fanzhenxing
 * @create 2018/4/27 9:33 AM
 */
@Entity
@Table(name = "auth_client_service")
@Data
public class ClientService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String serviceId;
    private String description;
    private Date crtTime;
    private String crtUser;
    private String crtName;
    private String crtHost;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

}
