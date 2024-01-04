#!/bin/bash

# 获取脚本所在目录的绝对路径
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 转到项目根目录
cd "$SCRIPT_DIR/.."

# 创建目录用于存放所有的jar包
mkdir -p package
# 存放最终产物
mkdir -p package/result

# 动态配置、流控、无损上下线、负载均衡(sermant-template)、监控、标签路由、流量标签透传、注册迁移、springboot注册、服务可见性、离群实例摘除
mkdir -p package/dynamic-demo
mkdir -p package/flowcontrol-demo
mkdir -p package/grace-demo
mkdir -p package/load-balance-demo
mkdir -p package/monitor-demo
mkdir -p package/router-demo
mkdir -p package/tag-transmission-demo
mkdir -p package/registry-demo
mkdir -p package/springboot-registry-demo
mkdir -p package/visibility-demo
mkdir -p package/removal-demo
mkdir -p package/mq-consume-prohibition-demo
mkdir -p package/first-plugin-demo

# 按照文件名模式将对应的jar文件复制到对应目录
# 创建首个插件
find . -type d -name "agent" -exec cp -rv {} package/first-plugin-demo/ \;
# 动态配置
find . -type f -name "spring-provider.jar" -exec cp -v {} package/dynamic-demo/ \;
# 流控
find . -type f -name "spring-provider.jar" -exec cp -v {} package/flowcontrol-demo/ \;
# 无损上下线
find . -type f -name "nacos-rest-data.jar" -exec cp -v {} package/grace-demo/ \;
find . -type f -name "nacos-rest-consumer.jar" -exec cp -v {} package/grace-demo/ \;
find . -type f -name "nacos-rest-provider.jar" -exec cp -v {} package/grace-demo/ \;
# 负载均衡
find . -type f -name "resttemplate-consumer.jar" -exec cp -v {} package/load-balance-demo/ \;
find . -type f -name "resttemplate-provider.jar" -exec cp -v {} package/load-balance-demo/ \;
# 监控
find . -type f -name "monitor-demo.jar" -exec cp -v {} package/monitor-demo/ \;
# 标签路由
find . -type f -name "spring-cloud-router-consumer.jar" -exec cp -v {} package/router-demo/ \;
find . -type f -name "spring-cloud-router-provider.jar" -exec cp -v {} package/router-demo/ \;
find . -type f -name "spring-cloud-router-zuul.jar" -exec cp -v {} package/router-demo/ \;
# 流量标签透传
find . -type f -name "http-client-demo.jar" -exec cp -v {} package/tag-transmission-demo/ \;
find . -type f -name "http-server-demo.jar" -exec cp -v {} package/tag-transmission-demo/ \;
# 注册迁移
find . -type f -name "dubbo-registry-consumer.jar" -exec cp -v {} package/registry-demo/ \;
find . -type f -name "dubbo-registry-provider.jar" -exec cp -v {} package/registry-demo/ \;
find . -type f -name "spring-cloud-registry-consumer.jar" -exec cp -v {} package/registry-demo/ \;
find . -type f -name "spring-cloud-registry-provider.jar" -exec cp -v {} package/registry-demo/ \;
# SpringBoot注册
find . -type f -name "service-a.jar" -exec cp -v {} package/springboot-registry-demo/ \;
find . -type f -name "service-b.jar" -exec cp -v {} package/springboot-registry-demo/ \;
# 服务可见性
find . -type f -name "dubbo-integration-provider.jar" -exec cp -v {} package/visibility-demo/ \;
find . -type f -name "dubbo-integration-consumer.jar" -exec cp -v {} package/visibility-demo/ \;
# 消息队列禁止消费
find . -type f -name "kafka-demo.jar" -exec cp -v {} package/mq-consume-prohibition-demo \;
# 离群实例摘除
find . -type f -name "rest-consumer.jar" -exec cp -v {} package/removal-demo/ \;
find . -type f -name "rest-provider.jar" -exec cp -v {} package/removal-demo/ \;


# 打包
tar -czvf package/result/sermant-examples-first-plugin-demo-$*.tar.gz -C package/first-plugin-demo/ .
tar -czvf package/result/sermant-examples-dynamic-demo-$*.tar.gz -C package/dynamic-demo/ .
tar -czvf package/result/sermant-examples-flowcontrol-demo-$*.tar.gz -C package/flowcontrol-demo/ .
tar -czvf package/result/sermant-examples-grace-demo-$*.tar.gz -C package/grace-demo/ .
tar -czvf package/result/sermant-examples-load-balance-demo-$*.tar.gz -C package/load-balance-demo/ .
tar -czvf package/result/sermant-examples-monitor-demo-$*.tar.gz -C package/monitor-demo/ .
tar -czvf package/result/sermant-examples-router-demo-$*.tar.gz -C package/router-demo/ .
tar -czvf package/result/sermant-examples-tag-transmission-demo-$*.tar.gz -C package/tag-transmission-demo/ .
tar -czvf package/result/sermant-examples-registry-demo-$*.tar.gz -C package/registry-demo/ .
tar -czvf package/result/sermant-examples-springboot-registry-demo-$*.tar.gz -C package/springboot-registry-demo/ .
tar -czvf package/result/sermant-examples-visibility-demo-$*.tar.gz -C package/visibility-demo/ .
tar -czvf package/result/sermant-examples-removal-demo-$*.tar.gz -C package/removal-demo/ .
tar -czvf package/result/sermant-examples-mq-consume-prohibition-demo-$*.tar.gz -C package/mq-consume-prohibition-demo/ .