package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Dao.NeoEntityRepository;
import com.example.neo4jKG.Dao.RelateRepository;
import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.Util.MathUtil;
import com.example.neo4jKG.Util.TransVOAndPOUtil;
import com.example.neo4jKG.VO.NeoAndRelationListVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NeoEntityServiceImpl implements NeoEntityService {

    @Autowired
    private NeoEntityRepository neoEntityRepository;

    @Autowired
    private RelateRepository relateRepository;

    @Autowired
    private TransVOAndPOUtil transVOAndPOUtil;

    @Autowired
    private MathUtil mathUtil;

    @Override
    public NeoEntityVO addNeoEntity(NeoEntityVO neoEntityVO) {

        NeoEntity neoEntity = neoEntityRepository.save(transVOAndPOUtil.transNeoEntityVO(neoEntityVO));
        NeoEntityVO neoEntityVORes = transVOAndPOUtil.transNeoEntity(neoEntity);
        neoEntityVORes.setSymbolSize(30);
        neoEntityVORes.setCategory(3);
        neoEntityVORes.setX(20.0);
        neoEntityVORes.setY(20.0);
        return neoEntityVORes;
    }

    @Override
    public void deleteByIdCus(Long id) {
        neoEntityRepository.deleteByIdCus(id);
    }

    @Override
    public NeoEntityVO findById(Long id) {
        Optional<NeoEntity> neoEntity= neoEntityRepository.findById(id);
        if(neoEntity.isPresent()&& neoEntity.get().getId().equals(id)){
            return transVOAndPOUtil.transNeoEntity(neoEntity.get());
        }else{
            return null;
        }
    }

    @Override
    public List<NeoEntity> findAll() {
        return neoEntityRepository.findAll();
    }

    @Override
    public RelationVO addIRelates(Long from, Long to, boolean isSolid, String des) {
        Optional<NeoEntity> fromOpt = neoEntityRepository.findById(from);
        Optional<NeoEntity> toOpt = neoEntityRepository.findById(to);
        if(fromOpt.isPresent() && fromOpt.get().getId().equals(from) && toOpt.isPresent() && toOpt.get().getId().equals(to)){
            Relation newRelation = new Relation(-1L,fromOpt.get(),toOpt.get(),isSolid, des);
            return transVOAndPOUtil.transRelation(relateRepository.save(newRelation));
        }else {
            return null;
        }
    }

    @Override
    public NeoEntityVO updateByEntity(NeoEntityVO neoEntityVO) {
        Optional<NeoEntity> neoEntityOpt = neoEntityRepository.findById(neoEntityVO.getId());
        if(!neoEntityOpt.isPresent())
            return null;
        NeoEntity neoEntityReal = neoEntityOpt.get();
        double centerX, centerY;
        if(neoEntityReal.isCenter()){
            centerX = neoEntityVO.getX();
            centerY = neoEntityVO.getY();
        }else {
            centerX = neoEntityReal.getCenterX();
            centerY = neoEntityReal.getCenterY();
        }
        System.out.println("neoEntityVO:"+neoEntityVO);
        NeoEntity neoEntity = neoEntityRepository.updateByEntity(neoEntityVO.getId(), neoEntityVO.getName(),
                neoEntityVO.getX(), neoEntityVO.getY(), neoEntityVO.getDes(),
                neoEntityVO.getCategory(), neoEntityVO.getSymbolSize(),centerX,centerY);
        return transVOAndPOUtil.transNeoEntity(neoEntity);
    }



    @Override
    public void deleteRelateById(long fromId, long toId) {
        relateRepository.deleteByNodeId(fromId, toId);
    }


    @Override
    public NeoAndRelationListVO getAllEntitiesAndRelations() {
        NeoAndRelationListVO neoAndRelationListVO = new NeoAndRelationListVO();
        List<NeoEntity> neoEntities = neoEntityRepository.findAll();
        List<Relation> relations = relateRepository.findAll();

        // 结果列表
        List<NeoEntityVO> neoEntityVOS = new ArrayList<>();
        List<RelationVO> relationVOS = new ArrayList<>();

        /* 决定点的半径和种类
        将半径定在30-70之间
        取最大的为70,最小的为30,按节点所拥有的关系数量比例分布
        */

        ArrayList<Integer> relationNum = new ArrayList<>();
        for(NeoEntity neoEntity:neoEntities){
            int cnt = relateRepository.countFromRelationById(neoEntity.getId()) + relateRepository.countToRelationById(neoEntity.getId());
            relationNum.add(cnt);
        }

        int maxNum = Collections.max(relationNum);
        int minNum = Collections.min(relationNum);

        for(int i=0;i<relationNum.size();i++){
            int symbolSize = 70 - (int)((maxNum-relationNum.get(i)) / (maxNum-minNum)) * (70-30);
            neoEntities.get(i).setSymbolSize(symbolSize);
            if(symbolSize >=60){
                neoEntities.get(i).setCategory(1);
            }else if(symbolSize <= 40){
                neoEntities.get(i).setCategory(3);
            }else {
                neoEntities.get(i).setSymbolSize(2);
            }
        }

        /*
        * 确定节点的x，y坐标
        * 首先寻找中心节点，将以中心节点为起始的节点均匀分布在四周，半径定为5
        * 中心节点x加12，确定下一个中心节点的位置，接着对下一个中心节点进行迭代
        * 中心节点的顺序按照symbolSize排序
        * 将已经定位的节点存在数组中
        * */
        neoEntities.sort((o1, o2) -> o2.getSymbolSize() - o1.getSymbolSize());
        List<Long> locatedIds = new ArrayList<>();
        // 当前中心节点x,y坐标
        double baseX = 10;
        double baseY = 10;

        for(NeoEntity neoEntity:neoEntities){
            int cnt = relateRepository.countFromRelationById(neoEntity.getId());
            List<NeoEntity> relatedNeoEntityList = neoEntityRepository.findAllRelatedNodes(neoEntity.getId());
            // 如果该点已经定位, 则根据中心节点更改其位置,且以该点baseX和baseY
            if (locatedIds.contains(neoEntity.getId())){
                if(neoEntity.getX()>neoEntity.getCenterX()){
                    neoEntity.setX(2*neoEntity.getX()-neoEntity.getCenterX()+2);
                }else {
                    neoEntity.setX(2*neoEntity.getX()-neoEntity.getCenterX()-2);
                }
                if(neoEntity.getY()>neoEntity.getCenterY()){
                    neoEntity.setY(2*neoEntity.getY()-neoEntity.getCenterY()+2);
                }else {
                    neoEntity.setY(2*neoEntity.getY()-neoEntity.getCenterY()-2);
                }
                baseX = neoEntity.getX();
                baseY = neoEntity.getY();
            }else{
                // 否则设置x和y
                locatedIds.add(neoEntity.getId());
                neoEntity.setX(baseX);
                neoEntity.setY(baseY);
                neoEntity.setCenterX(baseX);
                neoEntity.setCenterY(baseY);
            }
            // 设置相关节点的位置
            if (relatedNeoEntityList==null)
                continue;
            double angleInterval = mathUtil.remainTwoFractions(360.0/cnt);
            double curAngle = angleInterval/2;
            for(NeoEntity relatedNeoEntity:relatedNeoEntityList){
                if (locatedIds.contains(relatedNeoEntity.getId()))
                    continue;

                relatedNeoEntity.setX(mathUtil.remainTwoFractions(baseX+5*Math.cos(curAngle)));
                relatedNeoEntity.setY(mathUtil.remainTwoFractions(baseY+5*Math.sin(curAngle)));
                relatedNeoEntity.setCenterX(baseX);
                relatedNeoEntity.setCenterY(baseY);
                curAngle += angleInterval;
            }
            // 更新basex和y
            baseX += 12;
        }

        for(NeoEntity neoEntity:neoEntities){
            NeoEntityVO neoEntityVO = transVOAndPOUtil.transNeoEntity(neoEntity);
            neoEntityVOS.add(neoEntityVO);
        }

        for(Relation relation:relations){
            RelationVO relationVO = transVOAndPOUtil.transRelation(relation);
            relationVOS.add(relationVO);
        }

        neoAndRelationListVO.setCategories(3);
        neoAndRelationListVO.setLinks(relationVOS);
        neoAndRelationListVO.setNodes(neoEntityVOS);
        return neoAndRelationListVO;
    }

    @Override
    public void clearRepository() {
        neoEntityRepository.deleteAll();
        relateRepository.deleteAll();
    }
}
