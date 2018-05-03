package com.base.vo;

import com.base.jwt.IJWTInfo;
import lombok.Data;

/**
 * @author fanzhenxing
 * @create 2018/4/27 10:21 AM
 */
@Data
public class ClientInfo implements IJWTInfo {

    private String clientId;
    private String name;
    private String id;

    public ClientInfo(String clientId, String name, String id) {
        this.clientId = clientId;
        this.name = name;
        this.id = id;
    }

    @Override
    public String getUniqueName() {

        return clientId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
