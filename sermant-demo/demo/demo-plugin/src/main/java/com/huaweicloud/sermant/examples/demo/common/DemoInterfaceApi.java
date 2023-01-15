/*
 * Copyright (C) 2021-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.sermant.examples.demo.common;

/**
 * 用于测试为被增强类添加接口，该接口使用{@link DemoInterfaceImpl}实现
 *
 * @author HapThorin
 * @version 1.0.0
 * @since 2022-01-22
 */
public interface DemoInterfaceApi {
    /**
     * 测试方法
     */
    void foo();
}