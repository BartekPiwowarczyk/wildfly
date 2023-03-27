package com.example.wildfly.security;

import com.example.wildfly.model.dto.AlbumDTO;
import com.example.wildfly.model.entity.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class UserService implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    EntityManager em;

    @Inject
    UserMapper userMapper;


    public void createNewUser (User user) {
        LOGGER.info("create new User: {}, password:{}, role: {}",user.getUsername(),user.getPassword(),user.getRoleName());
        em.persist(user);
        LOGGER.info("User added to db");

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
        List<UserDTO> usersDTO = users.stream().map(user -> userMapper.fromUser(user)).collect(Collectors.toList());
        return usersDTO;
    }

    public List<UserDTO> getAllUsersWithCriteria() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
       Join<User,UserRoles> userRolesJoin = (Join<User, UserRoles>) root.fetch(User_.roleName);
//        Join<AlbumSong, Song> songJoin = (Join<AlbumSong, Song>) root.fetch(Album_.albumSongs, JoinType.LEFT).fetch(AlbumSong_.song, JoinType.LEFT);

                LOGGER.info("getAllUsersWithCriteria ");

        List<User> users = em.createQuery(query).getResultList();
        LOGGER.info("getAllUsersWithCriteria + query");
       List<UserDTO> usersDTO = users.stream()
                .map(user -> userMapper.fromUser(user))
                .collect(Collectors.toList());
        LOGGER.info("getAllUsersWithCriteria + dto");
        return usersDTO;

    }


    public User getUser(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join<User, UserRoles> userRolesJoin = (Join<User, UserRoles>) root.fetch(User_.roleName);

        query.select(root).where(
                criteriaBuilder.equal(root.get(User_.username), criteriaBuilder.parameter(String.class, "name"))
        ).distinct(true);

        return em.createQuery(query).setParameter("name", name).getResultList().stream().findFirst().orElseThrow(() -> new NotFoundException());
    }


    public void editUser(User userToUpdate) {
        User user = getUser(userToUpdate.getUsername());

        user.setPassword(userToUpdate.getPassword());
        LOGGER.info("before edit list");
        user.setRoleName(userToUpdate.getRoleName());
        LOGGER.info("username: {}, password: {}, roles: {}",user.getUsername(),user.getPassword(),user.getRoleName());
        em.merge(user);

    }

    public void deleteUser(String username) {
            LOGGER.info("deleteUser : {}",username);
            User reference = em.getReference(User.class, username);;
            LOGGER.info("Referencja : {}",reference);
            em.remove(reference);
    }

    public List<UserRoles> getAllRoles() {
        List<UserRoles> rolesList = em.createNamedQuery("User.findAllRoles", UserRoles.class).getResultList();
        LOGGER.info("Roles from db: {}",rolesList);
        return rolesList;
    }

}
