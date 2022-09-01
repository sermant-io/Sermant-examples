package com.huawei.dubbotest.service.imp;

import com.huawei.dubbotest.domain.Test;
import com.huawei.dubbotest.service.ATest;
import com.huawei.dubbotest.service.BTest;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component
public class ATestImp implements ATest {

    @Resource(name = "bTest")
    private BTest bTest;

    @Override
    public Map<String, String> testEmpty(long id, String name) {
        return bTest.testEmpty(id, name);
    }

    @Override
    public Map<String, String> testObject(Test test) {
        return bTest.testObject(test);
    }

    @Override
    public Map<String, String> testEnabled(Test test) {
        return bTest.testEnabled(test);
    }

    @Override
    public Map<String, String> testArray(String[] array) {
        return bTest.testArray(array);
    }

    @Override
    public Map<String, String> testList(List<String> list) {
        return bTest.testList(list);
    }

    @Override
    public Map<String, String> testMap(Map<String, String> map) {
        return bTest.testMap(map);
    }
}
