package com.example.neo4jKG.Controller;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relate;
import com.example.neo4jKG.Service.NeoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NeoEntityController {

    @Autowired
    private NeoEntityService neoEntityService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public NeoEntity addNeoEntity(@RequestBody NeoEntity neoEntity) {
        return neoEntityService.create(neoEntity);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public int deleteNeoEntityById(@RequestParam(value = "id") long id) {
        neoEntityService.deleteById(id);
        System.out.println(id);
        return 1;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public NeoEntity updateNeoEntityByEntity(@RequestBody NeoEntity neoEntity) {
        return neoEntityService.updateByEntity(neoEntity.getId(),neoEntity.getName());
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public NeoEntity getNeoEntityById(@RequestParam(value = "id") long id) {
        Optional<NeoEntity> optionalNeoEntity = neoEntityService.findById(id);
        return optionalNeoEntity.orElse(null);
    }

    @RequestMapping(path = "/addRelates", method = RequestMethod.POST)
    public Relate addRelateById(@RequestParam(value = "from") long from, @RequestParam(value = "to") long to, @RequestParam(value = "isSolid") boolean isSolid) {
        Optional<NeoEntity> fromOpt = neoEntityService.findById(from);
        Optional<NeoEntity> toOpt = neoEntityService.findById(to);
        if (fromOpt.isPresent() && toOpt.isPresent()) {
            return neoEntityService.addIRelates(fromOpt.get(), toOpt.get(), isSolid);
        } else {
            return null;
        }
    }

    @RequestMapping(path = "/delRelate", method = RequestMethod.GET)
    public String deleteRelateById(@RequestParam(value = "from") long from, @RequestParam(value = "to") long to){
        Optional<NeoEntity> fromOpt = neoEntityService.findById(from);
        Optional<NeoEntity> toOpt = neoEntityService.findById(to);
        if(fromOpt.isPresent() && toOpt.isPresent()){
            neoEntityService.deleteRelateById(from, to);
            return "ok";
        }else {
            return "false";
        }
    }
}

