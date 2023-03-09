package com.example.wildfly.service;


import com.example.wildfly.model.dto.ArtistDTO;
import com.example.wildfly.model.entity.Artist;
import com.example.wildfly.model.entity.Artist_;
import com.example.wildfly.model.mapper.ArtistMapper;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Stateless
public class ArtistService {

    @PersistenceContext
    EntityManager em;

    @Inject
    ArtistMapper artistMapper;


    public ArtistDTO getArtistDTOById(Long id) {
        return em.createNamedQuery("Artist.findById",ArtistDTO.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

    public Artist findOrCreateArtist(ArtistDTO artistDTO) {
        if (artistDTO == null) {
            Artist artist = em.createNamedQuery("Artist.findUnknownArtist",Artist.class).getSingleResult();
            artist.setName("Unknown");
            em.persist(artist);
            return artist;
        } else {
            Artist artist = em.createNamedQuery("Artist.findArtistByName", Artist.class)
                    .setParameter("name", artistDTO.getName())
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);
            if (artist == null)
                artist = createNewArtist(artistDTO);
            return artist;
        }
    }


    public Artist createNewArtist(ArtistDTO artistDTO) {
        Artist artist = artistMapper.fromArtistDTO(artistDTO);
        em.persist(artist);
        return artist;
    }

    //SELECT NEW com.example.model.dto.ArtistDTO(a.name,a.firstname) FROM Artist a where a.id= :id


    public ArtistDTO getArtistDTOForIdWithCriteria(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ArtistDTO> query = criteriaBuilder.createQuery(ArtistDTO.class);
        Root<Artist> root = query.from(Artist.class);

        query.select(criteriaBuilder.construct(ArtistDTO.class,root.get(Artist_.name),root.get(Artist_.firstname)))
                .where(criteriaBuilder.equal(root.get(Artist_.id),criteriaBuilder.parameter(Long.class,"id")));

        return em.createQuery(query).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

//    SELECT a FROM Artist a where a.name= :name


    public Artist getArtistByNameWithCriteria(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Artist> query = criteriaBuilder.createQuery(Artist.class);
        Root<Artist> root = query.from(Artist.class);

        query.where(criteriaBuilder.equal(root.get(Artist_.name),criteriaBuilder.parameter(String.class,"name")));

        return em.createQuery(query).setParameter("name",name).getResultList().stream().findFirst().orElse(null);
    }
}
