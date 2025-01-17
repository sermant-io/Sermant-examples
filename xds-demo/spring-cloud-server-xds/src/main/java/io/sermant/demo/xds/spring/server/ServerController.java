/*
 * Copyright (C) 2024-2024 Sermant Authors. All rights reserved.
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

package io.sermant.demo.xds.spring.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ServerController
 *
 * @author daizhenyu
 * @since 2024-10-11
 **/
@RestController
public class ServerController {
    private static final int ITERATION_COUNT = 15000;

    @Value("${SERVER_VERSION:v1}")
    private String version;

    /**
     * router
     *
     * @return result
     */
    @RequestMapping("router")
    public String router() throws UnknownHostException {
        StringBuilder builder = new StringBuilder();
        builder.append("spring-server version: ");
        builder.append(version);
        builder.append("; spring-server ip: ");
        builder.append(InetAddress.getLocalHost().getHostAddress());
        mockRealLogic();
        return builder.toString();
    }

    private void mockRealLogic() {
        double result = 0.0;
        for (int i = 0; i < ITERATION_COUNT; i++) {
            result += Math.sin(i) * Math.cos(i);
        }
    }
}
