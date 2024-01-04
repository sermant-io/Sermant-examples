# 工程介绍

|        示例名称        |                                         插件文档                                         |                       示例项目                       |
|:------------------:|:------------------------------------------------------------------------------------:|:------------------------------------------------:|
|     去源码插件开发模板      |             [去源码插件开发说明](https://sermant.io/zh/document/developer-guide/)             |      [first-plugin-demo](./first-plugin-demo)      |
|     动态配置插件使用示例     |       [动态配置插件使用说明](https://sermant.io/zh/document/plugin/dynamic-config.html)        |      [flowcontrol-demo](./flowcontrol-demo)      |
|      流控插件使用示例      |          [流控插件使用说明](https://sermant.io/zh/document/plugin/flowcontrol.html)          |      [flowcontrol-demo](./flowcontrol-demo)      |
|    无损上下线插件使用示例     |          [无损上下线插件使用说明](https://sermant.io/zh/document/plugin/graceful.html)          |            [grace-demo](./grace-demo)            |
|     负载均衡插件使用示例     |        [负载均衡插件使用说明](https://sermant.io/zh/document/plugin/loadbalancer.html)         |     [loadbalancer-demo](./loadbalancer-demo)     |
|      监控插件使用示例      |            [监控插件使用说明](https://sermant.io/zh/document/plugin/monitor.html)            |          [monitor-demo](./monitor-demo)          |
|     标签路由插件使用示例     |           [标签路由插件使用说明](https://sermant.io/zh/document/plugin/router.html)            |           [router-demo](./router-demo)           |
|    流量标签透传插件使用示例    |     [流量标签透传插件使用说明](https://sermant.io/zh/document/plugin/tag-transmission.html)      | [tag-transmission-demo](./tag-transmission-demo) |
|     注册迁移插件使用示例     |     [注册迁移插件使用说明](https://sermant.io/zh/document/plugin/register-migration.html)      |         [registry-demo](./registry-demo)         |
| SpringBoot注册插件使用示例 | [SpringBoot注册插件使用说明](https://sermant.io/zh/document/plugin/springboot-registry.html) |         [registry-demo](./registry-demo)         |
|    服务可见性插件使用示例     |         [服务可见性插件使用说明](https://sermant.io/zh/document/plugin/visibility.html)         |       [visibility-demo](./visibility-demo)       |
|    离群实例摘除插件使用示例    |          [离群实例摘除插件使用说明](https://sermant.io/zh/document/plugin/removal.html)          |          [removal-demo](./removal-demo)          |
|    消息队列禁止消费插件使用示例    |          [消息队列禁止消费插件使用说明](https://sermant.io/zh/document/plugin/mq-consume-prohibition.html)  |    [mq-consume-prohibition-demo](./mq-consume-prohibition-demo) |

# RELEASE打包流程

### 步骤一：增加新demo至打包脚本

> **若无新增demo，忽略此步**

在scripts/copy_jar.sh脚本中添加新demo相关命令

```shell
# 创建文件夹
mkdir -p package/xxxxx-demo
# 复制jar包
find . -type f -name "xxxxx-A.jar" -exec cp -v {} package/xxxxx-demo/ \;
find . -type f -name "xxxxx-B.jar" -exec cp -v {} package/xxxxx-demo/ \;
# 打包
tar -czvf package/result/sermant-examples-xxxxx-demo-$*.tar.gz -C package/xxxxx-demo/ .
```

### 步骤二：增加新demo至流水线文件

> **若无新增demo，忽略此步**

在.github/workflows/create_release.yml流水线文件中添加新demo相关命令

```shell
# 上传release包 ========================
- name: Upload Release xxxxx-demo # 上传xxxxx-demo release包
id: upload-release-asset-xxxxx
uses: actions/upload-release-asset@v1.0.2
env:
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
with:
  upload_url: ${{ steps.create_release.outputs.upload_url }}
  asset_path: ${{ github.workspace }}/package/result/sermant-examples-xxxxx-demo-${{ env.version }}.tar.gz
  asset_name: sermant-examples-xxxxx-demo-${{ env.version }}.tar.gz
  asset_content_type: application/tar
```

最后提交并合入上述修改

### 步骤三：创建新tag并推送

```shell
git tag vx.x.x
```

> 此步骤需拥有仓库直推权限