package com.baizhi.springbootselenium.controller;

import com.baizhi.springbootselenium.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zz_Wang
 * @time ---
 * @function
 */
@RestController
@RequestMapping("/api/product/")
public class CollectorController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/prices")
    public Map<String, Object> getPrices(@RequestParam("sku") String skuString) {
        logger.info("调用价格接口参数：【{}】", skuString);
        Map<String, Object> result = new HashMap();

        List<Map<String, Object>> resultList = new ArrayList();
        String[] skuArray = skuString.split(",");
        for (String sku : skuArray) {
            HashMap<String, Object> map = new HashMap();
            map.put("sku", sku);
            map.put("price", "100.0");
            map.put("mall_price", 100.0);
            map.put("exclude_tax_price", 87.0);
            map.put("tax_rate", 13);
            resultList.add(map);
        }
        result.put("success", true);
        result.put("result", resultList);

        logger.info("价格接口返回结果：【{}】", result);
        return result;
    }

    @PostMapping("/shelf_states")
    public Map<String, Object> getShelfStates(@RequestParam("sku") String skuString) {
        logger.info("调用上下架接口参数：【{}】", skuString);
        Map<String, Object> result = new HashMap();

        List<Map<String, Object>> resultList = new ArrayList();
        String[] skuArray = skuString.split(",");
        for (String sku : skuArray) {
            HashMap<String, Object> map = new HashMap();
            map.put("sku", sku);
            map.put("state", "1");
            resultList.add(map);
        }
        result.put("success", true);
        result.put("result", resultList);

        logger.info("上下架接口返回结果：【{}】", result);
        return result;
    }

    @PostMapping("/stocks")
    public Map<String, Object> getStocks(@RequestParam("sku") String skuString, @RequestParam("sku") String area) {
        logger.info("调用库存接口参数：【{}】", skuString);
        Map<String, Object> result = new HashMap();

        List<Map<String, Object>> resultList = new ArrayList();
        String[] skuArray = skuString.split(",");
        for (String sku : skuArray) {
            HashMap<String, Object> map = new HashMap();
            map.put("sku", sku);
            map.put("area", area);
            map.put("desc", "有货");
            map.put("num", "10");
            resultList.add(map);
        }
        result.put("success", true);
        result.put("result", resultList);

        logger.info("库存接口返回结果：【{}】", result);
        return result;
    }
}
