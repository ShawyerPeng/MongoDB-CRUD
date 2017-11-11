package dao.impl;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * spring mongodb 集成操作类
 */
public interface MongoBaseDao<T> {
    MongoCollection<Document> createCollection(Class<T> entityClass);

    <T> MongoCollection<Document> createCollection(String entityClass);

    void dropCollection(Class<T> entityClass);

    List<T> find(Query query);

    T findOne(Query query);

    T findById(String id);

    T findById(String id, String collectionName);

    void save(T entity);

    void insertAll(List<T> list);

    Long getPageCount(Query query);

    List<T> findEntityListByPage(Query query, Sort sort, int page, int pageSize);

    void update(Query query, Update update);

    void updateFirst(Query query, Update update);

    void updateMulti(Query query, Update update);

    void updateInsert(Query query, Update update);

    void remove(Query query);

    void delete(T t);

    void deleteById(String id);

    /**
     * 实体Class,需要子类重写该方法
     * 钩子方法,由子类实现返回反射对象的类型
     * 泛型中定义钩子方法, 然后 Dao 类继承抽象类, 实现该钩子方法, 返回反射的类型
     */
    Class<T> getEntityClass();

    MongoTemplate getMongoTemplate();

    void setMongoTemplate(MongoTemplate mongoTemplate);
}