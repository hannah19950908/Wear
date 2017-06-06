package com.controller;

import com.Exception.TokenException;
import com.service.MeasureService;
import com.service.TestService;
import com.service.TokenService;
import com.util.JSONUtils;
import com.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by 63289 on 2017/5/8.
 */
@CrossOrigin
@RequestMapping(value = "api", produces = "application/json;charset=UTF-8")
@RestController
public class TestController {
    private final TokenService tokenService;
    private final MeasureService measureService;
    private final TestService testService;

    @Autowired
    public TestController(TokenService tokenService, MeasureService measureService, TestService testService) {
        this.tokenService = tokenService;
        this.measureService = measureService;
        this.testService = testService;
    }

    @RequestMapping({"{token}/date/time"})
    public String testTimeCost(@PathVariable String token, @RequestParam(required = false) Long timeMills) throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        if (accountNumber == null) throw new TokenException();
        LocalTime startTime = LocalTime.now();
        if (timeMills == null) measureService.findTodayDataByAccountNumber(accountNumber);
        else measureService.findByAccountNumberAndDate(accountNumber, new Timestamp(timeMills));
        Long useTime = LocalTime.now().toNanoOfDay() - startTime.toNanoOfDay();
        Map map = new HashMap();
        map.put("costTimeInMiili", useTime / 1000000);
        return JSONUtils.toJSON(map);
    }
    @RequestMapping({"{token}/data/time"})
    public String testTimeCostLikeData
            (@PathVariable String token, @RequestParam(required = false) Long fromTimeMills, @RequestParam(required = false) Long toTimeMills)
            throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        LocalTime startTime = LocalTime.now();
        if (fromTimeMills == null && toTimeMills == null) {
            measureService.findAllDataByAccountNumber(accountNumber);
        } else if (toTimeMills == null)
            measureService.findByAccountNumberAndFromTime(accountNumber, new Timestamp(fromTimeMills));
        else if (fromTimeMills == null)
            measureService.findByAccountNumberAndCommitTime(accountNumber, new Timestamp(toTimeMills));
        else
            measureService.findByAccountNumberAndCommitTime(accountNumber, new Timestamp(fromTimeMills), new Timestamp(toTimeMills));
        Long useTime = LocalTime.now().toNanoOfDay() - startTime.toNanoOfDay();
        Map map = new HashMap();
        map.put("costTimeInMiili", useTime / 1000000);
        return JSONUtils.toJSON(map);
    }
    @RequestMapping("{token}/random/{number}")
    public void generateData(@PathVariable String token, @PathVariable Integer number) throws Exception {
        String accountNumber = tokenService.getAccountNumber(token);
        testService.randomGenerate(accountNumber, number);
    }
    @RequestMapping("/payload")
    public String payloadJSONRPC(@RequestHeader HttpHeaders httpHeaders, @RequestBody String mapString) throws Exception{
        Map map = JSONUtils.parseMap(mapString);
        Map params=(Map)map.get("params");
        if((map.get("method")).equals("login")) return testService.caclulatePayload(httpHeaders,mapString,params);
        String token=(String)params.get("token");
        return testService.caclulatePayload(httpHeaders,mapString,token);
    }
    @RequestMapping("/token/payload")
    public String loginPayloadREST(@RequestHeader HttpHeaders httpHeaders, @RequestBody String mapString) throws Exception{
        return testService.caclulatePayload(httpHeaders,mapString, JSONUtils.parseMap(mapString));
    }
    @RequestMapping("{token}/payload")
    public String getInfoPayloadREST(@RequestHeader HttpHeaders httpHeaders, @PathVariable String token) throws Exception{
        return testService.caclulatePayload(httpHeaders,null,token);
    }
}
