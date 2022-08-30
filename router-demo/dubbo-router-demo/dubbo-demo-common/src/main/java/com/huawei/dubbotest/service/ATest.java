package com.huawei.dubbotest.service;

import com.huawei.dubbotest.domain.Test;

import java.util.List;
import java.util.Map;

public interface ATest {
    Map<String, String> testEmpty(long id, String name);

    Map<String, String> testObject(Test test);

    Map<String, String> testEnabled(Test test);

    Map<String, String> testArray(String[] array);

    Map<String, String> testList(List<String> list);

    Map<String, String> testMap(Map<String, String> map);
}
