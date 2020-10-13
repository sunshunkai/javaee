package com.ssk.mylabel.parser;

import com.ssk.mylabel.bean.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author ssk
 * @date 2020/9/23
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }


    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String userName = element.getAttribute("userName");
        String email = element.getAttribute("email");

        if(StringUtils.hasText(userName)){
            bean.addPropertyReference("userName",userName);
        }
        if(StringUtils.hasText(email)){
            bean.addPropertyReference("email",email);
        }
    }

}
