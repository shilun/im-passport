package com.passport.main.controller;

import com.common.exception.ApplicationException;
import com.common.util.IGlossary;
import com.common.util.StringUtils;
import com.common.util.model.GenderTypeEnum;
import com.passport.main.AbstractClientController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "公共接口")
@RestController
@RequestMapping(method = {RequestMethod.POST})
public class GlobalController extends AbstractClientController {

    private static Map<String, Class> glosseryItems;

    static {
        glosseryItems = new HashMap<>();
        glosseryItems.put("gendertype", GenderTypeEnum.class);
    }

    @RequestMapping(value = "/global/type/{type}")
    @ResponseBody
    public Map<String, Object> buildGlossery(@PathVariable("type") String type) {
        return buildMessage(()->{
                List<Map<String, Object>> keyValues = new ArrayList<Map<String, Object>>();
                if (StringUtils.isBlank(type)) {
                    throw new ApplicationException("buildGlossery Error unKnow type");
                }
                Class currentEnum = glosseryItems.get(type);
                for (Object o : currentEnum.getEnumConstants()) {
                    IGlossary v = (IGlossary) o;
                    HashMap<String, Object> item = new HashMap<>();
                    item.put("value", ((Enum)v).name());
                    item.put("name", v.getName());
                    keyValues.add(item);
                }
                return keyValues;
        });
    }
}
