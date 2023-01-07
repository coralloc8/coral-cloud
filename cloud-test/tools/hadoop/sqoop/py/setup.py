#!/usr/bin/python3
# -*- coding: utf-8 -*-
from setuptools import setup, find_packages

with open("README.md", "r", encoding="utf-8") as fh:
    long_description = fh.read()
    # print("long_description", long_description)

with open("requirements.txt", "r", encoding="utf-8") as re:
    install_requires = re.readlines()
    # print("install_requires", install_requires)


setup(
    name="py",
    version="1.0.0",
    author="huss",
    author_email="452327322@qq.com",
    description="sqoop job",
    long_description=long_description,
    long_description_content_type="text/markdown",
    # url="",
    include_package_data=True,
    packages=find_packages(),
    install_requires=install_requires,
    python_requires='>=3.6',
)
