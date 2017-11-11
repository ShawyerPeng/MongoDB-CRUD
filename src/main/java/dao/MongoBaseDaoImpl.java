package dao;

import com.mongodb.client.MongoCollection;
import dao.impl.MongoBaseDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * spring mongodb 集成操作类
 */
@Repository
public abstract class MongoBaseDaoImpl<T> implements MongoBaseDao<T> {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;

    /**
     * 创建collection
     */
    @Override
    public MongoCollection<Document> createCollection(Class<T> entityClass) {
        return mongoTemplate.createCollection(entityClass);
    }

    @Override
    public <T> MongoCollection<Document> createCollection(String entityClass) {
        return mongoTemplate.createCollection(entityClass);
    }

    /**
     * 删除collection
     */
    @Override
    public void dropCollection(Class<T> entityClass) {
        mongoTemplate.dropCollection(entityClass);
    }

    /**
     * 根据条件查询实体集合
     */
    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据主键查询一个实体
     */
    @Override
    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    /**
     * 保存一个实体
     */
    @Override
    public void save(T entity) {
        mongoTemplate.save(entity);
    }

    /**
     * 保存一个集合对象/批量保存实体
     */
    @Override
    public void insertAll(List<T> list) {
        mongoTemplate.insertAll(list);
    }

    /**
     * 根据条件查询记录总数/根据条件查询库中符合记录的总数,为分页查询服务
     */
    @Override
    public Long getPageCount(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 分页查询
     */
    @Override
    public List<T> findEntityListByPage(Query query, Sort sort, int page, int pageSize) {
        query.with(sort).skip((page - 1) * pageSize).limit(pageSize);
        return find(query);
    }

    @Override
    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    /**
     * 按主键修改
     * 如果文档中没有相关key 会新增 使用$set修改器
     */
    @Override
    public void updateFirst(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 修改多条/更新满足条件的所有记录
     */
    @Override
    public void updateMulti(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中
     */
    @Override
    public void updateInsert(Query query, Update update){
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 删除
     */
    @Override
    public void remove(Query query) {
        mongoTemplate.findAndRemove(query, this.getEntityClass());
    }

    /**
     * 删除对象
     */
    @Override
    public void delete(T t) {
        this.mongoTemplate.remove(t);
    }

    /**
     * 根据Id删除用户
     */
    @Override
    public void deleteById(String id) {
        Criteria criteria = Criteria.where("_id").in(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            if (null != query && this.findOne(query) != null) {
                this.delete((T) query);
            }
        }
    }

    /**
     * get mongoTemplate
     */
    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * 注入 mongodbTemplate
     */
    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}