package com.base.exception.auth;

import com.base.constants.CommonConstants;
import com.base.exception.BaseException;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:14 PM
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }
}
