package com.controller;

import com.service.MeasureService;
import com.service.TokenService;
import com.util.JSONUtils;
import com.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 63289 on 2017/4/19.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api", produces = "application/json;charset=UTF-8")
public class MeasureController {
    private final MeasureService measureService;
    private final TokenService tokenService;

    @Autowired
    public MeasureController(MeasureService measureService, TokenService tokenService) {
        this.measureService = measureService;
        this.tokenService = tokenService;
    }

    @RequestMapping({"{token}/date"})
    public String getByDate(@PathVariable String token, @RequestParam(required = false) Long timeMills) throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        String s;
        if (timeMills == null) s = measureService.findTodayDataByAccountNumber(accountNumber);
        else s = measureService.findByAccountNumberAndDate(accountNumber, new Timestamp(timeMills));
        return "{\"measures\":"+s+"}";
    }

    @RequestMapping({"{token}/latest"})
    public String getEachDateLatestByDateRange
            (@PathVariable String token, @RequestParam(required = false) Long fromTimeMills, @RequestParam(required = false) Long toTimeMills)
            throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        List list;
        if (fromTimeMills == null && toTimeMills == null) {
            list = new ArrayList();
            list.add(measureService.findTheLatestByAccountNumber(accountNumber));
        } else if (fromTimeMills == null)
            list = measureService.findTheLatestOfDateByAccountNumberAndDateRange(accountNumber, new Timestamp(toTimeMills));
        else if (toTimeMills == null)
            list = measureService.findTheLatestOfDateByAccountNumberAndDateRange(accountNumber, new Timestamp(fromTimeMills), new Timestamp(System.currentTimeMillis()));
        else
            list = measureService.findTheLatestOfDateByAccountNumberAndDateRange(accountNumber, new Timestamp(fromTimeMills), new Timestamp(toTimeMills));
        return JSONUtils.toJSON(MapUtils.toMap(list));
    }

    @RequestMapping({"{token}/data"})
    public String getAllByTimeRange
            (@PathVariable String token, @RequestParam(required = false) Long fromTimeMills, @RequestParam(required = false) Long toTimeMills)
            throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        String s;
        if (fromTimeMills == null && toTimeMills == null) {
            s = measureService.findAllDataByAccountNumber(accountNumber);
        } else if (toTimeMills == null)
            s = measureService.findByAccountNumberAndFromTime(accountNumber, new Timestamp(fromTimeMills));
        else if (fromTimeMills == null)
            s = measureService.findByAccountNumberAndCommitTime(accountNumber, new Timestamp(toTimeMills));
        else
            s = measureService.findByAccountNumberAndCommitTime(accountNumber, new Timestamp(fromTimeMills), new Timestamp(toTimeMills));
        return "{\"measures\":"+s+"}";
    }

    @RequestMapping(value = "{token}", method = RequestMethod.POST)
    public void addData(@PathVariable String token, @RequestBody String mapString) throws Exception {
        Map map = JSONUtils.parseMap(mapString);
        String accountNumber = tokenService.getAccountNumber(token);
        Long commitTimeMills = (Long) map.get("commitTime");
        String device = (String) map.get("device");
        Integer step = (Integer) map.get("step");
        Integer distance = (Integer) map.get("distance");
        Integer heart = (Integer) map.get("heart");
        measureService.addData(accountNumber, commitTimeMills, device, step, distance, heart);
    }
}
