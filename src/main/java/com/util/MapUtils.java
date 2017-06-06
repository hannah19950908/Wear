package com.util;

import com.Exception.SQLNotFoundException;

import java.util.*;

/**
 * Created by 63289 on 2017/4/19.
 */
public class MapUtils {
    public static Map toMap(List list)throws Exception{
        Map map=new HashMap();
        return toMap(map,list);
    }
    public static Map toMap(Map map, List list)throws Exception{
        if(list==null||list.isEmpty()){
            throw new SQLNotFoundException();
        }else{
            map.put("measures",list);
        }
        return map;
    }
}
