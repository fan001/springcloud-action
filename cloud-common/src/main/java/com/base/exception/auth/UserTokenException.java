package com.base.exception.auth;

import com.base.constants.CommonConstants;
import com.base.exception.BaseException;

/**
 * @author fanzhenxing
 * @create 2018/4/26 3:16 PM
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
