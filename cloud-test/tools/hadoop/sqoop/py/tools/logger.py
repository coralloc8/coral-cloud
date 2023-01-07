#!/usr/bin/python3
# -*- coding: utf-8 -*-


import logging
import os
from datetime import datetime

from concurrent_log_handler import ConcurrentRotatingFileHandler

import colorlog

from config.global_var_model import LOG_PATH

if not os.path.exists(LOG_PATH):
    os.mkdir(LOG_PATH)  # 若不存在logs文件夹，则自动创建

log_colors_config = {
    # 终端输出日志颜色配置
    'DEBUG': 'white',
    'INFO': 'cyan',
    'WARNING': 'yellow',
    'ERROR': 'red',
    'CRITICAL': 'bold_red',
}

default_formats = {
    # 终端输出格式
    'color_format': '%(log_color)s%(asctime)s-%(name)s-%(filename)s-[line:%(lineno)d]-%(levelname)s-[日志信息]: %(message)s',
    # 日志输出格式
    'log_format': '%(asctime)s-%(name)s-%(filename)s-[line:%(lineno)d]-%(levelname)s-[日志信息]: %(message)s'
}


class HandleLog:
    """
    先创建日志记录器（logging.getLogger），然后再设置日志级别（logger.setLevel），
    接着再创建日志文件，也就是日志保存的地方（logging.FileHandler），然后再设置日志格式（logging.Formatter），
    最后再将日志处理程序记录到记录器（addHandler）
    """

    def __init__(self, level=logging.DEBUG):
        self.__now_time = datetime.now().strftime('%Y-%m-%d')  # 当前日期格式化
        self.__all_log_path = os.path.join(LOG_PATH, self.__now_time + "-all" + ".log")  # 收集所有日志信息文件
        self.__error_log_path = os.path.join(LOG_PATH, self.__now_time + "-error" + ".log")  # 收集错误日志信息文件
        self.__logger = logging.getLogger()  # 创建日志记录器
        self.__logger.setLevel(level)  # 设置默认日志记录器记录级别
        # 初始化
        self.__init()

    def __init(self):
        """
        初始化
        """
        all_logger_handler = self.__init_logger_handler(self.__all_log_path)  # 创建日志文件
        error_logger_handler = self.__init_logger_handler(self.__error_log_path)
        console_handle = self.__init_console_handle()

        self.__set_log_formatter(all_logger_handler)  # 设置日志格式
        self.__set_log_formatter(error_logger_handler)
        self.__set_color_formatter(console_handle, log_colors_config)

        self.__set_log_handler(all_logger_handler)  # 设置handler级别并添加到logger收集器
        self.__set_log_handler(error_logger_handler, level=logging.ERROR)
        self.__set_color_handle(console_handle)

    @staticmethod
    def __init_logger_handler(log_path):
        """
        创建日志记录器handler，用于收集日志
        :param log_path: 日志文件路径
        :return: 日志记录器
        """
        # 写入文件，如果文件超过10M大小时，切割日志文件，仅保留5个文件
        logger_handler = ConcurrentRotatingFileHandler(filename=log_path, maxBytes=10 * 1024 * 1024, backupCount=5,
                                             encoding='utf-8')
        return logger_handler

    @staticmethod
    def __init_console_handle():
        """创建终端日志记录器handler，用于输出到控制台"""
        console_handle = colorlog.StreamHandler()
        return console_handle

    def __set_log_handler(self, logger_handler, level=logging.DEBUG):
        """
        设置handler级别并添加到logger收集器
        :param logger_handler: 日志记录器
        :param level: 日志记录器级别
        """
        logger_handler.setLevel(level=level)
        self.__logger.addHandler(logger_handler)

    def __set_color_handle(self, console_handle):
        """
        设置handler级别并添加到终端logger收集器
        :param console_handle: 终端日志记录器
        :param level: 日志记录器级别
        """
        console_handle.setLevel(logging.DEBUG)
        self.__logger.addHandler(console_handle)

    @staticmethod
    def __set_color_formatter(console_handle, color_config):
        """
        设置输出格式-控制台
        :param console_handle: 终端日志记录器
        :param color_config: 控制台打印颜色配置信息
        :return:
        """
        formatter = colorlog.ColoredFormatter(default_formats["color_format"], log_colors=color_config)
        console_handle.setFormatter(formatter)

    @staticmethod
    def __set_log_formatter(file_handler):
        """
        设置日志输出格式-日志文件
        :param file_handler: 日志记录器
        """
        formatter = logging.Formatter(default_formats["log_format"], datefmt='%a, %d %b %Y %H:%M:%S')
        file_handler.setFormatter(formatter)

    @staticmethod
    def __close_handler(file_handler):
        """
        关闭handler
        :param file_handler: 日志记录器
        """
        file_handler.close()

    def get_logger(self):
        """
        获取logger
        """
        return self.__logger


logger = HandleLog(logging.INFO).get_logger()

if __name__ == '__main__':
    logger.info("这是日志信息%s", 222)
    logger.debug("这是debug信息")
    logger.warning("这是警告信息")
    logger.error("这是错误日志信息")
    logger.critical("这是严重级别信息")
