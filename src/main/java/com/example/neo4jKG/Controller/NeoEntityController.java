package com.example.neo4jKG.Controller;

import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.VO.NeoAndRelationListVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class NeoEntityController {

    @Autowired
    private NeoEntityService neoEntityService;

    // 增加实体
    @RequestMapping(path = "/addNeoEntity", method = RequestMethod.POST)
    public ResponseVO addNeoEntity(@RequestBody NeoEntityVO neoEntity) {
        return ResponseVO.buildSuccess(neoEntityService.addNeoEntity(neoEntity));
    }

    // 删除节点和节点有关的关系
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public ResponseVO deleteNeoEntityById(@RequestParam(value = "id") long id) {
        neoEntityService.deleteByIdCus(id);
        System.out.println(id);
        return ResponseVO.buildSuccess();
    }

    // 更新实体
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ResponseVO updateNeoEntityByEntity(@RequestBody NeoEntityVO neoEntityVO) {
        return ResponseVO.buildSuccess(neoEntityService.updateByEntity(neoEntityVO));
    }

    // 根据id获取实体
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseVO getNeoEntityById(@RequestParam(value = "id") long id) {
        return ResponseVO.buildSuccess(neoEntityService.findById(id));
    }

    // 增加关系(根据起始节点和终止节点)
    @RequestMapping(path = "/addRelates", method = RequestMethod.POST)
    public ResponseVO addRelateById(@RequestParam(value = "from") long from, @RequestParam(value = "to") long to, @RequestParam(value = "isSolid") boolean isSolid,
                                    @RequestParam(value = "des") String des, @RequestParam(value = "name") String name) {
//        NeoEntityVO fromOpt = neoEntityService.findById(from);
//        NeoEntityVO toOpt = neoEntityService.findById(to);
//        if (fromOpt != null && toOpt != null) {
//            return neoEntityService.addIRelates(fromOpt, toOpt, isSolid, des);
//        } else {
//            return null;
//        }
        return ResponseVO.buildSuccess(neoEntityService.addIRelates(from, to, isSolid, des, name));
    }

    // 根据id删除关系
    @RequestMapping(path = "/delRelate", method = RequestMethod.GET)
    public ResponseVO deleteRelateById(@RequestParam(value = "id") long id){
        neoEntityService.deleteRelateById(id);
        return ResponseVO.buildSuccess();
    }

    // 获取所有的实体和关系
    @RequestMapping(path = "/getListAll", method = RequestMethod.GET)
    public ResponseVO getListAll(){
        return ResponseVO.buildSuccess(neoEntityService.getAllEntitiesAndRelations());
    }

    // 根据id更新关系名称
    @RequestMapping(path = "/updateRel", method = RequestMethod.GET)
    public ResponseVO updateRel(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name){
        neoEntityService.updateRel(id,name);
        return ResponseVO.buildSuccess();
    }
}

