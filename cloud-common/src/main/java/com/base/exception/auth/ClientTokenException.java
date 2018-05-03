package com.base.exception.auth;

import com.base.constants.CommonConstants;
import com.base.exception.BaseException;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:15 PM
 */
public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
