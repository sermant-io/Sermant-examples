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

package io.sermant.xds.service.discovery;

import io.sermant.core.plugin.agent.entity.ExecuteContext;
import io.sermant.core.plugin.agent.interceptor.Interceptor;
import io.sermant.core.plugin.config.PluginConfigManager;
import io.sermant.core.service.ServiceManager;
import io.sermant.core.service.xds.XdsCoreService;
import io.sermant.core.service.xds.XdsServiceDiscovery;
import io.sermant.core.service.xds.entity.ServiceInstance;

import java.util.Iterator;
import java.util.Set;

/**
 * interceptor
 *
 * @author daizhenyu
 * @since 2024-7-05
 **/
public class TemplateInterceptor implements Interceptor {
    private final XdsServiceDiscovery xdsServiceDiscovery;

    private final TemplateConfig templateConfig;

    public TemplateInterceptor() {
        xdsServiceDiscovery = ServiceManager.getService(XdsCoreService.class)
                .getXdsServiceDiscovery();
        templateConfig = PluginConfigManager.getPluginConfig(TemplateConfig.class);
    }

    @Override
    public ExecuteContext before(ExecuteContext context) {
        System.out.println("server name" + templateConfig.getUpstreamServiceName());
        Set<ServiceInstance> serviceInstance = xdsServiceDiscovery
                .getServiceInstance(templateConfig.getUpstreamServiceName());
        Iterator<ServiceInstance> iterator = serviceInstance.iterator();
        if (iterator.hasNext()) {
            ServiceInstance instance = iterator.next();
            StringBuilder builder = new StringBuilder();
            builder.append("http://");
            builder.append(instance.getHost());
            builder.append(":");
            builder.append(instance.getPort());
            Object[] arguments = context.getArguments();
            arguments[0] = builder.toString();
        }
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }

    @Override
    public ExecuteContext onThrow(ExecuteContext context) {
        return context;
    }
}
