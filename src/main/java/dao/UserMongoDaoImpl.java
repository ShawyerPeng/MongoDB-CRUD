package dao;

import po.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserMongoDaoImpl extends MongoBaseDaoImpl<User> {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}