package com.example.wildfly.security;

import com.example.wildfly.model.dto.AlbumDTO;
import com.example.wildfly.model.entity.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class UserService {

    Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    EntityManager em;

    @Inject
    UserMapper userMapper;


    public void createNewUser (User user,UserRoles userRole) {
        LOGGER.info("create new User: {}, password:{}, role: {}",user.getUsername(),user.getPassword(), user.getUserRoles());
        em.persist(user);
        em.persist(userRole);
        LOGGER.info("User added to db");

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
//        LOGGER.info("role get(1): " + users.get(1).getUserRoles().get(1).getRole());
//        LOGGER.info("role get(0): " + users.get(1).getUserRoles().get(0).getRole());

        List<UserDTO> usersDTO = users.stream().map(user -> userMapper.fromUser(user)).collect(Collectors.toList());

        return usersDTO;
    }

    public List<UserDTO> getAllUsersWithCriteria() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join<User, UserRoles> userRolesJoin = root.join(User_.userRoles);
//        Join<AlbumSong, Song> songJoin = (Join<AlbumSong, Song>) root.fetch(Album_.albumSongs, JoinType.LEFT).fetch(AlbumSong_.song, JoinType.LEFT);

        List<User> users = em.createQuery(query).getResultList();
//        LOGGER.info("role get(1): " + users.get(1).getUserRoles().get(1).getRole());
//        LOGGER.info("role get(0): " + users.get(1).getUserRoles().get(0).getRole());
        return users.stream()
                .map(user -> userMapper.fromUser(user))
                .collect(Collectors.toList());

    }


}
