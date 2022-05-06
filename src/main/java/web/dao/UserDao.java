package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public User findById(Long id) {
       return entityManager.find(User.class, id);
    }
    @Transactional
    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }
    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
    @Transactional
    public void updateUser(Long id, User user) {
        User user1 = findById(id);
        entityManager.merge(user);
    }
}
