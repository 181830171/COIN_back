package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Dao.NeoEntityRepository;
import com.example.neo4jKG.Dao.RelateRepository;
import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.Util.TransVOAndPOUtil;
import com.example.neo4jKG.VO.NeoAndRelationListVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;
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

    @Autowired
    private TransVOAndPOUtil transVOAndPOUtil;

    @Override
    public NeoEntityVO addNeoEntity(NeoEntityVO neoEntity) {

        NeoEntity neoEntity1 = neoEntityRepository.save(transVOAndPOUtil.transNeoEntityVO(neoEntity));

        return transVOAndPOUtil.transNeoEntity(neoEntity1);
    }

    @Override
    public void deleteByIdCus(Long id) {
        neoEntityRepository.deleteByIdCus(id);
    }

    @Override
    public NeoEntityVO findById(Long id) {
        Optional<NeoEntity> neoEntity= neoEntityRepository.findById(id);
        return neoEntity.map(entity -> transVOAndPOUtil.transNeoEntity(entity)).orElse(null);
    }

    @Override
    public List<NeoEntity> findAll() {
        return neoEntityRepository.findAll();
    }

    @Override
    public RelationVO addIRelates(NeoEntityVO from, NeoEntityVO to, boolean isSolid) {
        Relation newRelation = new Relation(-1L,transVOAndPOUtil.transNeoEntityVO(from),transVOAndPOUtil.transNeoEntityVO(to),isSolid);
        return transVOAndPOUtil.transRelation(relateRepository.save(newRelation));
    }

    @Override
    public NeoEntityVO updateByEntity(Long id, String name, int x, int y, String des, int category, int symbolSize) {
        NeoEntity neoEntity = neoEntityRepository.updateByEntity(id, name, x, y, des, category, symbolSize);
        return transVOAndPOUtil.transNeoEntity(neoEntity);
    }



    @Override
    public void deleteRelateById(long fromId, long toId) {
        relateRepository.deleteByNodeId(fromId, toId);
    }

    @Override
    public NeoAndRelationListVO getAllEntitiesAndRelations() {
        List<NeoEntity> neoEntities = neoEntityRepository.findAll();
        List<Relation> relations = relateRepository.findAll();
        return null;
    }
}
