#-*-coding:utf-8-*-

###
### stage record
### by raphyer

import os,sys
sys.path.append(sys.path[0][:(sys.path[0].rindex((os.sep + "src")))])

import src.utils.util as util

class StageRecord:

    def __init__(self, name):

        self._name = name
        self._count = 0
        self._occurTime = -1

    def incCount(self):
        self._count += 1

    def setOccurTime(self, t):
        self._occurTime = t

    def occurTime(self):
        return self._occurTime

    def insertSql(self):

        fieldSql = ""
        valSql = ""

        fieldSql += "%s, %s_t, %s_count," % (self._name, self._name, self._name)

        valSql += "%d, '%s', %d," % (self._occurTime, util.isoTimeString(self._occurTime/1000.0, False), self._count)

        return (fieldSql, valSql)