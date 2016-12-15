# UniversalDependencyBuilder

A simple tool for creating training files for GNT and MDParser using conllu files.

It works for UD versions 1.2 and 1.3

When features are changed make sure to run 

com.gn.data.ConlluToConllMapper.runUDversion_1_3()

first

before calling the GNT and MDP training factory!

Otherwise, the old corpus and data properties will be used!
