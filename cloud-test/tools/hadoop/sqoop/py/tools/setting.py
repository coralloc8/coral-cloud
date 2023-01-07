#!/usr/bin/python3
# -*- coding: utf-8 -*-
import os
from abc import ABCMeta, abstractmethod

import yaml


class ListKey:
    __metaclass__ = ABCMeta

    @abstractmethod
    def getKey(self):
        pass


class DatabaseConfig(yaml.YAMLObject, ListKey):
    yaml_loader = yaml.SafeLoader
    yaml_tag = "!database"

    def __init__(self, key, hostname, port, username, password, db_survey):
        self.key = key
        self.hostname = hostname
        self.port = port
        self.username = username
        self.password = password
        self.db_survey = db_survey

    def getKey(self):
        return self.key


def load_yml(config_path: str):
    """
    加载yaml文件
    """
    path = os.path.join(base_path(), config_path)
    with open(path, 'r', encoding='utf-8') as file:
        generator = yaml.load_all(file, Loader=yaml.SafeLoader)
        return list(generator)


def search_key(items: list, key: str):
    """
    查询list中的某个元素 item为ListKey的实现类

    """
    for li in items:
        if li.getKey() == key.strip():
            return li
    return None


def base_path():
    return os.path.abspath(os.path.join(os.path.abspath(os.path.dirname(__file__)), "../"))
