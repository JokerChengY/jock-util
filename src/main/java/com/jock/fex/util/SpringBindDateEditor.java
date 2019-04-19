package com.jock.fex.util;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author yangcs
 * @version V1.0
 * @since 2017-11-21
 * @Description 类说明:解决空字符串转date是转换异常问题
 */
public class SpringBindDateEditor extends PropertyEditorSupport {

    Logger logger = Logger.getLogger(SpringBindDateEditor.class);

    public SpringBindDateEditor() {
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            super.setValue(text);
        }
    }

}
