package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Dao.CategoryRepository;
import com.example.neo4jKG.Dao.NeoEntityRepository;
import com.example.neo4jKG.Dao.RelateRepository;
import com.example.neo4jKG.Dao.SearchHistoryRepository;
import com.example.neo4jKG.Driver.NeoEntityDriver;
import com.example.neo4jKG.Entity.Category;
import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.Entity.SearchHistory;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.Util.MathUtil;
import com.example.neo4jKG.Util.TransVOAndPOUtil;
import com.example.neo4jKG.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class NeoEntityServiceImpl implements NeoEntityService {

    @Autowired
    private NeoEntityRepository neoEntityRepository;

    @Autowired
    private RelateRepository relateRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private NeoEntityDriver neoEntityDriver;

    @Autowired
    private TransVOAndPOUtil transVOAndPOUtil;

    @Autowired
    private MathUtil mathUtil;

    /**
     * 增加新的实体
     * 调用服务:NeoEntityRepository.save()
     * @param neoEntityVO
     * @return
     */
    @Override
    public NeoEntityVO addNeoEntity(NeoEntityVO neoEntityVO) {

        neoEntityVO.setNodeId((long) -1);
        NeoEntity neoEntity = neoEntityRepository.save(transVOAndPOUtil.transNeoEntityVO(neoEntityVO));
        NeoEntityVO neoEntityVORes = transVOAndPOUtil.transNeoEntity(neoEntity);
        neoEntityVORes.setSymbolSize(30);
        neoEntityVORes.setCategory((long)2);
        neoEntityVORes.setX(20.0);
        neoEntityVORes.setY(20.0);
        return neoEntityVORes;
    }

    /**
     * 根据id删除节点
     * 调用服务 neoEntityDriver.deleteNode
     * @param id
     */
    @Override
    public void deleteByIdCus(Long id) {
        neoEntityDriver.deleteNode(id);
    }


    /**
     * 根据id查找节点
     * 调用服务 NeoEntityRepository.findById
     * @param id
     * @return
     */
    @Override
    public NeoEntityVO findById(Long id) {
        Optional<NeoEntity> neoEntity= neoEntityRepository.findById(id);
        if(neoEntity.isPresent()&& neoEntity.get().getId().equals(id)){
            return transVOAndPOUtil.transNeoEntity(neoEntity.get());
        }else{
            return null;
        }
    }

    /**
     * 根据名称查找实体
     * 调用服务 NeoEntityRepository.findByName
     * @param name
     * @return
     */
    @Override
    public NeoEntity findByName(String name) {
        List<NeoEntity> neoEntityList = neoEntityRepository.findByName(name);
        if(neoEntityList == null || neoEntityList.size()==0){
            return null;
        }
        return neoEntityList.get(0);
    }

    /**
     * 查找所有实体
     * NeoEntityRepository.findAll
     * @return
     */
    @Override
    public List<NeoEntity> findAll() {
        return neoEntityRepository.findAll();
    }

    /**
     * 添加关系
     * 调用服务 relateRepository.save
     * @param from
     * @param to
     * @param isSolid
     * @param des
     * @param name
     * @return
     */
    @Override
    public RelationVO addIRelates(Long from, Long to, boolean isSolid, String des, String name, String[] symbol) {
        Optional<NeoEntity> fromOpt = neoEntityRepository.findById(from);
        Optional<NeoEntity> toOpt = neoEntityRepository.findById(to);
        if(fromOpt.isPresent() && fromOpt.get().getId().equals(from) && toOpt.isPresent() && toOpt.get().getId().equals(to)){
            Relation newRelation = new Relation(-1L,fromOpt.get(),toOpt.get(),isSolid, des, name,symbol);
            return transVOAndPOUtil.transRelation(relateRepository.save(newRelation));
        }else {
            return null;
        }
    }

    /**
     * 更新实体
     * 调用服务: NeoEntityRepository.updateByEntity
     * @param neoEntityVO
     * @return
     */
    @Override
    public NeoEntityVO updateByEntity(NeoEntityVO neoEntityVO) {
        Optional<NeoEntity> neoEntityOpt = neoEntityRepository.findById(neoEntityVO.getNodeId());
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
        String symbol=neoEntityVO.getSymbol();
        if(symbol==null){
            symbol="circle";
        }
//        System.out.println("neoEntityVO:"+neoEntityVO);
        NeoEntity neoEntity = neoEntityRepository.updateByEntity(neoEntityVO.getNodeId(), neoEntityVO.getName(),
                neoEntityVO.getX(), neoEntityVO.getY(), neoEntityVO.getDes(),
                neoEntityVO.getCategory(), neoEntityVO.getSymbolSize(),centerX,centerY,symbol);
        return transVOAndPOUtil.transNeoEntity(neoEntity);
    }


    /**
     * 根据id删除关系
     * 调用服务 NeoEntityDriver.deleteRel
     * @param id
     */
    @Override
    public void deleteRelateById(long id) {
        neoEntityDriver.deleteRel(id);
    }

    /**
     * initAllEntitiesAndRelations
     * 初始化所有节点位置和大小
     * 调用服务 neoEntityRepository.findAll,neoEntityRepository.updateByEntity
     */
    @Override
    public NodeListVO initAllEntities(){
        NodeListVO nodeList=new NodeListVO();
        List<NeoEntity> neoEntities = neoEntityRepository.findAll();
        /* 决定点的半径和种类
        将半径定在30-70之间
        取最大的为70,最小的为30,按节点所拥有的关系数量比例分布
        */

        ArrayList<Integer> relationNum = new ArrayList<>();
        for(NeoEntity neoEntity:neoEntities){
            int cnt = neoEntityDriver.findRelatedNodes(neoEntity.getId()).size();
            relationNum.add(cnt);
        }

        int maxNum = Collections.max(relationNum);
        int minNum = Collections.min(relationNum);

        for(int i=0;i<relationNum.size();i++){
            int symbolSize = 70 - (int)(((maxNum-relationNum.get(i)) / (double)(maxNum-minNum)) * (70-30));
            NeoEntity neoEntity=neoEntities.get(i);
            neoEntity.setSymbolSize(symbolSize);

            if(neoEntity.getCategory()==null){
                if(symbolSize >=60){
//                    neoEntity.setCategory((long)0);
                }else if(symbolSize <= 40){
//                    neoEntity.setCategory((long)1);
                }else {
//                    neoEntity.setCategory((long)2);
                }
            }
        }

        /*
         * 确定节点的x，y坐标
         * 首先寻找中心节点，将和中心节点有关的节点均匀分布在四周，半径定为5
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

            List<NeoEntity> relatedNeoEntityList = neoEntityDriver.findRelatedNodes(neoEntity.getId());
            // 如果该点已经定位, 则根据中心节点更改其位置,且以该点baseX和baseY
            if (locatedIds.contains(neoEntity.getId())){
                if(neoEntity.getX()>neoEntity.getCenterX()){
                    neoEntity.setX(mathUtil.remainTwoFractions(2*neoEntity.getX()-neoEntity.getCenterX()+2));
                }else {
                    neoEntity.setX(mathUtil.remainTwoFractions(2*neoEntity.getX()-neoEntity.getCenterX()-2));
                }
                if(neoEntity.getY()>neoEntity.getCenterY()){
                    neoEntity.setY(mathUtil.remainTwoFractions(2*neoEntity.getY()-neoEntity.getCenterY()+2));
                }else {
                    neoEntity.setY(mathUtil.remainTwoFractions(2*neoEntity.getY()-neoEntity.getCenterY()-2));
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

            int cnt = relatedNeoEntityList.size();
            double angleInterval = mathUtil.remainTwoFractions(360.0/cnt);
            double curAngle = angleInterval/2;
            for(NeoEntity relatedNeoEntity:relatedNeoEntityList){
                if (locatedIds.contains(relatedNeoEntity.getId()))
                    continue;
                for(NeoEntity entity:neoEntities){
                    if(entity.getId().equals(relatedNeoEntity.getId())){
                        entity.setX(mathUtil.remainTwoFractions(baseX+5*Math.cos(curAngle)));
                        entity.setY(mathUtil.remainTwoFractions(baseY+5*Math.sin(curAngle)));
                        entity.setCenterX(baseX);
                        entity.setCenterY(baseY);
                        break;
                    }
                }
                curAngle += angleInterval;
                locatedIds.add(relatedNeoEntity.getId());
            }
            // 更新basex和y
            baseX += 12;
            baseY += mathUtil.remainTwoFractions(Math.random()*4-2);
        }


        for(NeoEntity neoEntity:neoEntities){
            neoEntityRepository.updateByEntity(neoEntity.getId(),neoEntity.getName(),neoEntity.getX()
            ,neoEntity.getY(),neoEntity.getDes(),neoEntity.getCategory(),neoEntity.getSymbolSize(),neoEntity.getCenterX()
            ,neoEntity.getCategory(),neoEntity.getSymbol());
        }
        nodeList.setNodes(neoEntities);
        return nodeList;


    }



    /**
     * 获取所有的实体和关系
     * 调用服务:
     * relateRepository.findAll 获取所有的关系
     * neoEntityDriver.findRelatedNodes 根据id获取相关节点
     * relateRepository.findLimitedAll 获取一定数量的关系
     * @return
     */
    @Override
    public NeoAndRelationListVO getAllEntitiesAndRelations() {
        System.out.println("getALLlISTANDENTITYIES:" +  "Enter");
        NeoAndRelationListVO neoAndRelationListVO = new NeoAndRelationListVO();
        List<NeoEntity> neoEntities = new ArrayList<>();
        HashSet<Long> neoEntityIds = new HashSet<>();
        List<Relation> tmp_relations = relateRepository.findLimitedAll();
        List<Relation> relations = new ArrayList<>();
        for(int i=0;i<tmp_relations.size();i++){
            Relation relation = relateRepository.findById(tmp_relations.get(i).getRelationshipId()).get();
            relations.add(relation);
        }
        System.out.println("getALLlISTANDENTITYIES:" +  relations.size());
        for(Relation relation:relations){
            System.out.println(relation);
            if(!neoEntityIds.contains(relation.getFrom().getId())){
                neoEntityIds.add(relation.getFrom().getId());
                neoEntities.add(relation.getFrom());
            }
            if(!neoEntityIds.contains(relation.getTo().getId())){
                neoEntityIds.add(relation.getTo().getId());
                neoEntities.add(relation.getTo());
            }
        }
        System.out.println("getALLlISTANDENTITYIES:" +  neoEntities.size());
        List<Category> categories= categoryRepository.findAll();
        // 结果列表
        List<NeoEntityVO> neoEntityVOS = new ArrayList<>();
        List<RelationVO> relationVOS = new ArrayList<>();
        List<CategoryVO> categoryVOS=new ArrayList<>();


        for(Category category:categories){
            CategoryVO categoryVO=transVOAndPOUtil.transCategory(category);
            categoryVOS.add(categoryVO);

        }

        for(NeoEntity neoEntity:neoEntities){
            NeoEntityVO neoEntityVO = transVOAndPOUtil.transNeoEntity(neoEntity);
            neoEntityVOS.add(neoEntityVO);
        }

        for(Relation relation:relations){
            RelationVO relationVO = transVOAndPOUtil.transRelation(relation);
            relationVOS.add(relationVO);
        }

        neoAndRelationListVO.setCategories(categoryVOS);
        neoAndRelationListVO.setLinks(relationVOS);
        neoAndRelationListVO.setNodes(neoEntityVOS);
        return neoAndRelationListVO;
    }

    /**
     * 清空数据库
     * 调用服务: neoEntityDriver.clearRepository
     */
    @Override
    public void clearRepository() {
        neoEntityDriver.clearRepository();
    }

    /**
     * 根据id更新关系名称
     * 调用服务: neoEntityDriver.updateRelation
     * @param id
     * @param name
     */
    @Override
    public void updateRel(long id, String name) {
        neoEntityDriver.updateRelation(id, name);
    }

    /**
     * 修改关系的虚实线
     * @param id,isSolid
     * @return
     */
    @Override
    public ResponseVO updateRelType(long id,String type){
        Optional<Relation> relationOpt = relateRepository.findById(id);
        if(!relationOpt.isPresent()) {
            return ResponseVO.buildFailure("关系不存在");
        }

        neoEntityDriver.updateRelType(id,type);

        return ResponseVO.buildSuccess();
    }

    /**
     * 修改关系的两端形状
     * @param id,symbol
     * @return
     */
    @Override
    public ResponseVO updateRelSymbol(long id,String[] symbol){
        Optional<Relation> relationOpt = relateRepository.findById(id);
        if(!relationOpt.isPresent()) {
            return ResponseVO.buildFailure("关系不存在");
        }
        neoEntityDriver.updateRelSymbol(id,symbol);
        return ResponseVO.buildSuccess();
    }

    /**
     * 增加新的节点类型
     * 调用CategoryRepository.save()
     * @param name,color,symbol
     * @return
     */
    @Override
    public CategoryVO addCategory(String name,String color){
        CategoryVO categoryVO=new CategoryVO();
        categoryVO.setCategoryId((long) -1);
        categoryVO.setName(name);
        categoryVO.setItemStyle(new ItemStyleVO(color));
        Category category=categoryRepository.save(transVOAndPOUtil.transCategoryVO(categoryVO));
        return transVOAndPOUtil.transCategory(category);
    }

    /**
     * 修改节点类型
     * 调用CategoryRepository.updateCategory()
     * @param id,name,color
     * @return
     */
    @Override
    public ResponseVO updateCategory(long id,String name,String color){
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if(!categoryOpt.isPresent()) {
            return ResponseVO.buildFailure("类型不存在");
        }

        Category category=categoryRepository.updateCategory(id, name, color);
        return ResponseVO.buildSuccess(transVOAndPOUtil.transCategory(category));
    }

    /**
     * 获取搜索历史
     * 调用searchHistoryRepository.findAll()
     */

    @Override
    public ResponseVO getSearchHistories(){
        List<SearchHistory> searchHistories=searchHistoryRepository.findAll();
        List<String> historyList=new ArrayList<>();
        for(SearchHistory searchHistory:searchHistories){
            historyList.add(searchHistory.getHistory());
        }
        return ResponseVO.buildSuccess(historyList);
    }

    /**
     * 搜索节点，支持模糊查询，保存搜索记录
     * @param message
     */
    @Override
    public ResponseVO searchNodes(String message) {
        SearchHistory searchHistory=new SearchHistory();
        searchHistory.setId((long)-1);
        searchHistory.setHistory(message);
        //保存搜索记录
        List<SearchHistory> searchHistories=searchHistoryRepository.findByHistory(message);
        if(searchHistories.size()==0) {
            searchHistoryRepository.save(searchHistory);
        }

        ArrayList<Long> nodeIds=new ArrayList<>();
        //根据文本直接查询部分
        List<NeoEntity> neoEntities=neoEntityRepository.searchNodes(message);
        List<NeoEntityVO> neoEntityVOS=new ArrayList<>();
        for(NeoEntity neoEntity:neoEntities){
            neoEntityVOS.add(transVOAndPOUtil.transNeoEntity(neoEntity));
            nodeIds.add(neoEntity.getId());
        }
        //智能搜索
        try {
            //路径对应chatbot.py
            String path=".\\chatbot\\chatbot.py";
            //参数“1”/“0”对应否/是采用多轮问答
            String[] args1 = new String[] { "python",path, message,"1"};
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            proc.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);

                //如果该行不是提示行，则搜索答案有关的节点
                if(!line.startsWith("*")) {
                    neoEntities = neoEntityRepository.findByName(line);
                    for (NeoEntity neoEntity : neoEntities) {
                        if(!nodeIds.contains(neoEntity.getId())) {
                            neoEntityVOS.add(transVOAndPOUtil.transNeoEntity(neoEntity));
                            nodeIds.add(neoEntity.getId());
                        }
                    }
                }
            }

            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseVO.buildSuccess(neoEntityVOS);
    }
}
