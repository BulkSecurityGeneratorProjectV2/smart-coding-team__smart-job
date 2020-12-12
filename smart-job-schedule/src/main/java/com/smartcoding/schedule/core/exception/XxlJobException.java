

package com.smartcoding.schedule.core.exception;

/**
 * @author xuxueli 2019-05-04 23:19:29
 */
public class XxlJobException extends RuntimeException {

    public XxlJobException() {
    }
    public XxlJobException(String message) {
        super(message);
    }

    public XxlJobException(String message, Throwable cause) {
        super(message, cause);
    }

}
