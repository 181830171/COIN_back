package com.example.neo4jKG.Controller;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.VO.NeoAndRelationListVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NeoEntityController {

    @Autowired
    private NeoEntityService neoEntityService;

    @RequestMapping(path = "/addNeoEntity", method = RequestMethod.POST)
    public NeoEntityVO addNeoEntity(@RequestBody NeoEntityVO neoEntity) {
        return neoEntityService.addNeoEntity(neoEntity);
    }

    // 删除节点和节点有关的关系
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public int deleteNeoEntityById(@RequestParam(value = "id") long id) {
        neoEntityService.deleteByIdCus(id);
        System.out.println(id);
        return 1;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public NeoEntityVO updateNeoEntityByEntity(@RequestBody NeoEntityVO neoEntityVO) {
        return neoEntityService.updateByEntity(neoEntityVO.getId(),neoEntityVO.getName(),neoEntityVO.getX(),neoEntityVO.getY()
                , neoEntityVO.getDes(), neoEntityVO.getCategory(), neoEntityVO.getSymbolSize());
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public NeoEntityVO getNeoEntityById(@RequestParam(value = "id") long id) {
        return neoEntityService.findById(id);
    }

    @RequestMapping(path = "/addRelates", method = RequestMethod.POST)
    public RelationVO addRelateById(@RequestParam(value = "from") long from, @RequestParam(value = "to") long to, @RequestParam(value = "isSolid") boolean isSolid) {
        NeoEntityVO fromOpt = neoEntityService.findById(from);
        NeoEntityVO toOpt = neoEntityService.findById(to);
        if (fromOpt != null && toOpt != null) {
            return neoEntityService.addIRelates(fromOpt, toOpt, isSolid);
        } else {
            return null;
        }
    }

    @RequestMapping(path = "/delRelate", method = RequestMethod.GET)
    public String deleteRelateById(@RequestParam(value = "from") long from, @RequestParam(value = "to") long to){
        NeoEntityVO fromOpt = neoEntityService.findById(from);
        NeoEntityVO toOpt = neoEntityService.findById(to);
        if(fromOpt != null && toOpt != null){
            neoEntityService.deleteRelateById(from, to);
            return "ok";
        }else {
            return "false";
        }
    }

    @RequestMapping(path = "/getListAll", method = RequestMethod.GET)
    public NeoAndRelationListVO getListAll(){
        return null;
    }
}

