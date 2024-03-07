/*
 *  Copyright (C) 2024-2024 Huawei Technologies Co., Ltd. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.huaweicloud.sermant.mongodb.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * operate mongodb
 *
 * @author daizhenyu
 * @since 2024-03-05
 **/
@RestController
public class MongoDbController {
    private static final int STRING_LENGTH = 21;

    @Value("${mongodb.address}")
    private String mongoDbAddress;

    @RequestMapping("createCollection")
    public String createCollection(String databaseName, String collectionName) {
        try (MongoClient mongoClient = MongoClients.create(mongoDbAddress)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.createCollection(collectionName);
        } catch (Exception e) {
            return "Collection " + collectionName + " failed to be created";
        }
        return "Collection " + collectionName + " successfully created";
    }

    @RequestMapping("queryCollection")
    public String queryCollection(String databaseName) {
        try (MongoClient mongoClient = MongoClients.create(mongoDbAddress)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoIterable<String> strings = database.listCollectionNames();
            return buildCollectionsString(strings);
        }
    }

    private String buildCollectionsString(MongoIterable<String> strings) {
        StringBuilder builder = new StringBuilder();
        builder.append("Current Collection: [");
        for (String string : strings) {
            builder.append(string + ", ");
        }
        if (builder.length() != STRING_LENGTH) {
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("]");
        return builder.toString();
    }
}
