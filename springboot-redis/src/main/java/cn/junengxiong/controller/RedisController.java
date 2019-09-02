package cn.junengxiong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.junengxiong.bean.ReturnMap;
import cn.junengxiong.config.redis.RedisUtil;

@RestController
public class RedisController {

    @Autowired
    RedisUtil redisUtil;

    /**
     * set value
     * 
     * @param str   key
     * @param value v
     * @param db    db no
     * @return
     */
    @RequestMapping("/set/{key}/{value}/{db}")
    public ReturnMap set(@PathVariable("key") String key, @PathVariable("value") String value,
            @PathVariable("db") Integer db) {
        redisUtil.set(key, value, db);
        return new ReturnMap().success();
    }

    /**
     * get value
     * 
     * @param key
     * @param db  db no
     * @return
     */
    @RequestMapping("/get/{key}/{db}")
    public ReturnMap get(@PathVariable("key") String key, @PathVariable("db") Integer db) {
        return new ReturnMap().success().data(redisUtil.get(key, db));
    }
}
