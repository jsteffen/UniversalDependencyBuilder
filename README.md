# UniversalDependencyBuilder

A simple tool for creating model files for GNT and MDParser using conllu files.

It works for UD versions 1.2 and 1.3

When features are changed make sure to run 

com.gn.data.ConlluToConllMapper.runUDversion_1_3()

first

before calling the GNT and MDP training factory!

Otherwise, the old corpus and data properties will be used!

TODO:

-	Adapt it to newer UD version v1.4 etc
-	provide XML property file for setting up data sources/targets

