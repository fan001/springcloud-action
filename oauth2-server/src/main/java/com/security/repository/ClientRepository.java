package com.security.repository;

import com.security.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fanzhenxing
 * @create 2018/4/27 10:01 AM
 */
public interface ClientRepository extends JpaRepository<Client,Integer> {

    public Client findByCode(String code);

    public Client findByCodeAndSecret(String code,String secret);
}
