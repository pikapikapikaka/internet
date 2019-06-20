#-*-coding:utf-8-*-

###
### task data
### by raphyer

import os,sys
sys.path.append(sys.path[0][:(sys.path[0].rindex((os.sep + "src")))])

import src.stage_analyzer.stage_record as stage_record


class Task:

    def __init__(self):

        self._taskId = None
        self._appId = None
        self._taskType = None

        self._stageRecordMap = {}

    def _initData(self, stage):

        if not self._taskId:
            self._taskId = stage.taskId()

        if not self._appId:
            self._appId = stage.appId()

        if not self._taskType:
            self._taskType = stage.taskType()

    def taskId(self):
        return self._taskId

    def appId(self):
        return self._appId

    def taskType(self):
        return self._taskType

    def getRecord(self, name):

        if not self._stageRecordMap.has_key(name):
            self._stageRecordMap[name] = stage_record.StageRecord(name)

        return self._stageRecordMap.get(name)


    def buildStage(self, stage, builder):

        self._initData(stage)
        builder.build(self, stage)

    def insertSql(self, tableName):

        fieldSql = ""
        valSql = ""

        for stageRecord in self._stageRecordMap.values():
            (f1, v1) = stageRecord.insertSql()
            fieldSql += f1
            valSql += v1

        fieldSql += "task_id, app_id, task_type"
        valSql += "'%s', '%s', '%s'" % (self._taskId,
                                        self._appId if self._appId else "NULL",
                                        self._taskType if self._taskType else "NULL")

        sql = "INSERT INTO %s(%s) VALUE(%s)" % (tableName, fieldSql, valSql)
        return sql
