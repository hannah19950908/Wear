package com.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 63289 on 2017/5/11.
 */
public class IteratorUtils {
    public static List toList(Iterator iterator){
        List list=new LinkedList();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}
