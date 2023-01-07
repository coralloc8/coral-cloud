#!/usr/bin/python3
# -*- coding: utf-8 -*-
import os.path

from tools.setting import load_yml, base_path

# 基础路径
BASE_PATH = base_path()

# config配置文件路径
CONFIG_PATH = os.path.join(BASE_PATH, "config/application.yml")
# 日志文件路径
LOG_PATH = os.path.join(BASE_PATH, "logs/log")

data = load_yml(CONFIG_PATH)

# 数据源
DATABASES = data[0]
