#-*-coding:utf-8-*-

###
### stage builder
### by raphyer

STAGE_USE_MIN_TIME = 0
STAGE_USE_MAX_TIME = 1

STAGE_DEFAULT_TYPE = STAGE_USE_MIN_TIME


class Builder:

    def __init__(self, property):

        self._name = property[0]
        self._type = property[1]

        self._convertFun = None
        self._convertStageList = (self._name,)

        if len(property) == 4:
            self._convertFun = property[2]
            self._convertStageList = property[3]

    def build(self, task, stage):

        recordName = self._name

        if self._convertFun:
            recordName = self._convertFun(stage)

        record = task.getRecord(recordName)

        record.incCount()

        if record.occurTime() < 0:
            record.setOccurTime(stage.occurTime())
            return

        if self._type == STAGE_USE_MIN_TIME:
            if stage.occurTime() < record.occurTime():
                record.setOccurTime(stage.occurTime())
            return

        if self._type == STAGE_USE_MAX_TIME:
            if stage.occurTime() > record.occurTime():
                record.setOccurTime(stage.occurTime())
            return

    def createFieldSql(self):

        sql = ""
        for stageName in self._convertStageList:
            sql += "%s BIGINT DEFAULT -1," % (stageName,)
            sql += "%s_t DATETIME," % (stageName,)
            sql += "%s_count INT DEFAULT 0," % (stageName,)
        return sql
