package com.zobgo.job.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.applet.AppletContext;

/**
 * Created by zongbo.zhang on 10/25/18.
 */
public class AopTestImpl implements AopTestApi{

    @Override
    public void save() {
        System.out.println("保存到数据库－－－");
    }

}

class DBOption extends ApplicationObjectSupport {
    public void getBean(){
        ApplicationContext context = super.getApplicationContext();
        System.out.println(context);
        AopTestImpl aopTest = (AopTestImpl) context.getBean("AopTestImpl");
        aopTest.save();
    }



    public static void main(String[] args)  {
        DBOption dbOption = new DBOption();
        dbOption.getBean();
    }
}
