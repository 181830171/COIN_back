package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Dao.NeoEntityRepository;
import com.example.neo4jKG.Dao.RelateRepository;
import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relate;
import com.example.neo4jKG.Service.NeoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NeoEntityServiceImpl implements NeoEntityService {

    @Autowired
    private NeoEntityRepository neoEntityRepository;

    @Autowired
    private RelateRepository relateRepository;

    @Override
    public NeoEntity create(NeoEntity neoEntity) {
        return neoEntityRepository.save(neoEntity);
    }

    @Override
    public void deleteById(Long id) {
        neoEntityRepository.deleteById(id);
    }

    @Override
    public Optional<NeoEntity> findById(Long id) {
        return neoEntityRepository.findById(id);
    }

    @Override
    public List<NeoEntity> findAll() {
        return (List<NeoEntity>)neoEntityRepository.findAll();
    }

    @Override
    public Relate addIRelates(NeoEntity from, NeoEntity to, boolean isSolid) {
        Relate newRelate = new Relate(-1L,from,to,isSolid);
        return relateRepository.save(newRelate);
    }

    @Override
    public NeoEntity updateByEntity(Long id, String name) {
        return neoEntityRepository.updateByEntity(id, name);
    }

    @Override
    public void deleteRelateById(long fromId, long toId) {
        relateRepository.deleteByNodeId(fromId, toId);
    }
}
