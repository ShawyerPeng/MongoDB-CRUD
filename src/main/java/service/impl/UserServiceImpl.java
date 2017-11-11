package service.impl;

import dao.UserMongoDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import po.Address;
import po.User;
import service.UserService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml", "classpath:spring/applicationContext.xml"})
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMongoDaoImpl userMongoDao;

    private static String USER_COLLECTION = "userDocument";

    /**
     * 创建Collection
     */
    @Test
    public void createCollection() {
        //userMongoDao.createCollection("test");
        userMongoDao.createCollection(User.class);
    }

    /**
     * 删除Collection
     */
    @Test
    public void dropCollection() {
        //userMongoDao.dropCollection("test");
        userMongoDao.dropCollection(User.class);
    }

    /**
     * 插入数据
     */
    @Test
    public void insert() {
        userMongoDao.save(new User("ShawyerPeng", "123", new Address("Nanjing", 211100)));
    }

    @Test
    public void delete() {
        userMongoDao.remove(new Query(Criteria.where("loginName").is("Peng")));
    }

    @Test
    public void update() {
        Query query = new Query(Criteria.where("password").is("123"));
        Update update = Update.update("password", "123456");
        userMongoDao.updateFirst(query, update);
    }

    @Test
    public void findUserById() {
        System.out.println(userMongoDao.findById("1"));
    }

    @Test
    public void findUserByPassword() {
        List<User> userList = userMongoDao.find(new Query(Criteria.where("password").is("123")));
        System.out.println(userList);
    }
}