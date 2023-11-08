package com.huaweicloud.sermant.template;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.operation.OperationManager;
import com.huaweicloud.sermant.core.operation.converter.api.YamlConverter;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * 拦截器
 *
 * @author daizhenyu
 * @since 2023-10-19
 **/
public class TemplateInterceptor implements Interceptor {
    private static final Map<String, Boolean> CONFIG_MAP = new HashMap<>();

    private static final String ENABLE_KEY = "enable";

    // 获取动态配置服务实例
    private final DynamicConfigService dynamicConfigService = ServiceManager.getService(DynamicConfigService.class);

    // 获取Sermant框架提供的yaml解析器
    private final YamlConverter converter = OperationManager.getOperation(YamlConverter.class);

    // Sermant日志类
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String START_TIME = "startTime";

    {
        // 开关默认为true
        CONFIG_MAP.put(ENABLE_KEY, true);
        dynamicConfigService.addConfigListener("template-config", "sermant/template-plugin",
                new DynamicConfigListener() {
                    @Override
                    public void process(DynamicConfigEvent event) {
                        // 解析yaml文件 为map
                        Optional<Map<String, Object>> convertOptional = converter.convert(event.getContent(),
                                Map.class);
                        if (convertOptional.isPresent()) {
                            // 修改开关状态
                            CONFIG_MAP.put(ENABLE_KEY, (boolean) convertOptional.get().get(ENABLE_KEY));
                        }
                        System.out.println("插件配置项发生变化，配置项值为：" + event.getContent());
                    }
                });
    }

    @Override
    public ExecuteContext before(ExecuteContext context) {
        if (!CONFIG_MAP.get(ENABLE_KEY)) {
            System.out.println("不执行增强逻辑");
            return context;
        }
        context.setLocalFieldValue(START_TIME, System.currentTimeMillis());
        System.out.println("已记录方法运行开始的时间");
        // 打印warn级别日志，用于Backend观测
        LOGGER.warning("warning message");
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        if (!CONFIG_MAP.get(ENABLE_KEY)) {
            System.out.println("不执行增强逻辑");
            return context;
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - (long) context.getLocalFieldValue(START_TIME);
        LOGGER.info("方法耗时：" + elapsedTime);
        System.out.println("方法耗时：" + elapsedTime);
        // 打印error级别日志，用于Backend观测
        LOGGER.severe("error message");
        return context;
    }

    @Override
    public ExecuteContext onThrow(ExecuteContext context) {
        return context;
    }
}
