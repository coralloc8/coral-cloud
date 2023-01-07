# 项目依赖安装方式

* 安装pipreqs pip install pipreqs
* 依赖导出方式 此方式只会导出本项目需要使用到的依赖 `pipreqs ./ --encoding='utf8' --force`
* 依赖导出方式 此方式会将本地全部依赖导出   `pip freeze > requirements.txt`
* 依赖安装方式   `pip install -r requirements.txt`

# 升级pip

```
python -m ensurepip
python -m pip install --upgrade pip
```

# 打包步骤解释

* long_description：对项目比较长的描述，我们可以直接从 你刚刚写的 README 文件读取。
* name: 你定义的包名，可以用字母、数字、下划线，需要确保唯一性。
* version: 项目的版本号。
* author: 你（作者）的名称。
* author_email: 你（作者） 的邮箱。
* description: 项目的简要描述。
* long_description_content_type：长描述内容的使用的标记类型，一般为 markdown 或者 rst。
* url: 你这个项目的主页地址，也可以直接链接到你这个项目的Github 地址上面去。
* include_package_data: 是否添加 py 以外的文件。
* package_data: 需要添加 Python 的额外文件列表。
* packages: 直接用 setuptool 找到你项目所有相关的包列表。
* classifiers: 附加说明，比如这里写的就是使用于 Python3 版本，使用的是 MIT 协议，独立于 OS。
* python_requires: python 版本要求。

# 打包命令

* python目前主流的二进制包格式是wheel（.whl后缀），它的前身是egg。wheel本质也还是一个压缩包，可以像zip一样解压缩。还有其他一些格式诸如rpm、wininst等。
  在使用wheel之前，需要先安装wheel：
  ```
  pip install wheel
  pip install --upgrade setuptools
  pip install --upgrade pip
  ```

  这里以打包成rpm格式为例：
  `python setup.py bdist --format=rpm`
  等价于
  `python setup.py build_rpm`
  等价于
  `python setup.py bdist_rpm`

* 还有目前的主流二进制包，wheel是目前官方推荐的打包方式。
  `python setup.py build_wheel`
  等价于
  `python setup.py bdist_wheel`

* 生成源码包的路径和sdist相同
  eg: 如果使用bdist_wininst,打出来的是exe安装文件，可以点击安装

