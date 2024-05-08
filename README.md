# Project Introduction
[简体中文](README_zh.md) | [English](README.md)

|        Usage example       |                                         Plugin document                                         |                       Sample project                       |
|:------------------:|:------------------------------------------------------------------------------------:|:------------------------------------------------:|
|     Plugin development template      |             [Plugin development template](https://sermant.io/zh/document/developer-guide/)             |      [first-plugin-demo](./first-plugin-demo)      |
|     Dynamic configuration plugin     |       [Dynamic configuration plugin](https://sermant.io/zh/document/plugin/dynamic-config.html)        |      [flowcontrol-demo](./flowcontrol-demo)      |
|      Flow control plugin      |          [Flow control plugin ](https://sermant.io/zh/document/plugin/flowcontrol.html)          |      [flowcontrol-demo](./flowcontrol-demo)      |
|    Lossless online and offline plugin    |          [Lossless online and offline plugin](https://sermant.io/zh/document/plugin/graceful.html)          |            [grace-demo](./grace-demo)            |
|     Load balancer plugin     |        [Load balancer plugin](https://sermant.io/zh/document/plugin/loadbalancer.html)         |     [loadbalancer-demo](./loadbalancer-demo)     |
|      Monitor plugin      |            [Monitor plugin](https://sermant.io/zh/document/plugin/monitor.html)            |          [monitor-demo](./monitor-demo)          |
|      Router plugin     |           [Router plugin](https://sermant.io/zh/document/plugin/router.html)            |           [router-demo](./router-demo)           |
|    Tag transmission plugin    |     [Tag transmission plugin](https://sermant.io/zh/document/plugin/tag-transmission.html)      | [tag-transmission-demo](./tag-transmission-demo) |
|     Registry plugin     |     [Registry plugin](https://sermant.io/zh/document/plugin/register-migration.html)      |         [registry-demo](./registry-demo)         |
| SpringBoot registry plugin | [SpringBoot registry plugin](https://sermant.io/zh/document/plugin/springboot-registry.html) |         [registry-demo](./registry-demo)         |
|    Visibility plugin     |         [Visibility plugin](https://sermant.io/zh/document/plugin/visibility.html)         |       [visibility-demo](./visibility-demo)       |
|    Outlier instance removal plugin    |          [Outlier instance removal plugin](https://sermant.io/zh/document/plugin/removal.html)          |          [removal-demo](./removal-demo)          |
|    MQ consume prohibition plugin    |          [MQ consume prohibition plugin](https://sermant.io/zh/document/plugin/mq-consume-prohibition.html)  |    [mq-consume-prohibition-demo](./mq-consume-prohibition-demo) |

# Release Package

### Step 1: Add a new demo to the packaging script

> **If no demo is added, skip this step**

Add new demo related commands to the scripts/copy_jar.sh

```shell
# create folder
mkdir -p package/xxxxx-demo
# copy jar
find . -type f -name "xxxxx-A.jar" -exec cp -v {} package/xxxxx-demo/ \;
find . -type f -name "xxxxx-B.jar" -exec cp -v {} package/xxxxx-demo/ \;
# package
tar -czvf package/result/sermant-examples-xxxxx-demo-$*.tar.gz -C package/xxxxx-demo/ .
```

### Step 2: Add a new demo to the pipeline file

> **If no demo is added, skip this step**

Add new demo related commands to the .github/workflows/create_release.yml

```shell
# upload the release package ========================
- name: Upload Release xxxxx-demo # upload xxxxx demo release package
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

Finally, submit and merge  above changes

### Step 3: Create a new tag and push it

```shell
git tag vx.x.x
```

> This step requires direct push permission to this repository.