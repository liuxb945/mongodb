package com.abc.tool.mongo.example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.tool.mongo.dao.UserDao;
import com.abc.tool.mongo.dao.impl.UserDaoImpl;
import com.abc.tool.mongo.model.NameEntity;
import com.abc.tool.mongo.model.UserEntity;
  
public class ApplicationSpring {  
  
    public static void main(String[] args) {  
  
        System.out.println("Bootstrapping HelloMongo");  
  
        ConfigurableApplicationContext context = null;  
        context = new ClassPathXmlApplicationContext("classpath:/spring/spring-config-mongo.xml");  
  
        UserDao userDao = context.getBean(UserDaoImpl.class);  
            userDao._test();  
        UserEntity entity1 = new UserEntity();  
        entity1.setId("5");  
        entity1.setAge(1);  
        entity1.setBirth(new Date());  
        entity1.setPassword("asdfasdf");  
        entity1.setRegionName("北京");  
        entity1.setWorks(1);  
        userDao.insert(entity1); 
        NameEntity name=new NameEntity();
        name.setNickname("xiao");
        name.setUsername("limingnihao");
        entity1.setName(name);
        userDao.update(entity1);  
        //userDao.createCollection();  
      
        List<UserEntity> list = userDao.findList(0, 10);  
        for (UserEntity e : list) {  
            System.out.println("all - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName=" + e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial())  
                    + ", name=" + e.getName().getUsername() + "-" + e.getName().getNickname() + ", birth=" + e.getBirth());  
        }  
  
        list = userDao.findListByAge(1);  
        for (UserEntity e : list) {  
            System.out.println("age=1 - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName=" + e.getRegionName() + ", special="  
                    + Arrays.toString(e.getSpecial()) + ", name=" + e.getName().getUsername() + "-" + e.getName().getNickname() + ", birth=" + e.getBirth());  
        }  
  
        UserEntity e = userDao.findOne("1");
        if(e!=null)
        System.out.println("id=1 - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName=" + e.getRegionName() + ", special=" + Arrays.toString(e.getSpecial())  
                + ", name=" + e.getName().getUsername() + "-" + e.getName().getNickname() + ", birth=" + e.getBirth());  
  
        e = userDao.findOneByUsername("limingnihao");
        if(e!=null)
        System.out.println("username=limingnihao - id=" + e.getId() + ", age=" + e.getAge() + ", password=" + e.getPassword() + ", regionName=" + e.getRegionName() + ", special="  
                + Arrays.toString(e.getSpecial()) + ", name=" + e.getName().getUsername() + "-" + e.getName().getNickname() + ", birth=" + e.getBirth());  
  
          
        System.out.println("DONE!");  
    }  
  
}
