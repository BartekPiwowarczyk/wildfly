package com.example.wildfly.service;


import com.example.wildfly.model.dto.SongDTO;
import com.example.wildfly.model.entity.Song;
import com.example.wildfly.model.mapper.SongMapper;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SongService {

    @PersistenceContext
    EntityManager em;

//    @Inject
//    SongMapper songMapper;
    public SongDTO getSongDTOById(Long id) {
        return em.createNamedQuery("Song.findById", SongDTO.class).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }

//    @Transactional
//    public SongDTO createNewSong(SongDTO songDTO) {
//        Song song = songMapper.fromSongDTO(songDTO);
//        em.persist(song);
//        return songDTO;
//    }

//    SELECT NEW com.example.model.dto.SongDTO(s.title,s.remarks,s.duration) from Song s where s.id = :id

    public SongDTO getSongDTOByIdWithCriteria(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<SongDTO> query = criteriaBuilder.createQuery(SongDTO.class);
        Root<Song> song = query.from(Song.class);

        query.select(criteriaBuilder.construct(SongDTO.class,song.get("title"),song.get("remarks"),song.get("duration")))
                .where(criteriaBuilder.equal(song.get("id"),criteriaBuilder.parameter(Long.class,"id")));
        return em.createQuery(query).setParameter("id",id).getResultList().stream().findFirst().orElse(null);
    }
}
