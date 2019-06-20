#-*-coding:utf-8-*-

###
### stage analyzer
### by raphyer

import os,sys
sys.path.append(sys.path[0][:(sys.path[0].rindex((os.sep + "src")))])

import threading

import xlwt

import src.stage_analyzer.stage as stageM
import src.stage_analyzer.task as taskM
import src.constants as constants
import src.utils.sql_engine as sql_engine
import src.utils.util as util

import stage_builder

import src.utils.log as log
logger = log.getLogger("root")


IS_DEBUG_MODE = False

TABLE_NAME = "stage_analyzer"

WRITE_DB_THREAD = 50


STAGE_LIST = [
    ("Saver_Recv_TaskAddResource", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Recv_TaskModifyResource", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Recv_TaskAddScheduler", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Parser_TaskAddResource", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Parser_TaskModifyResource", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Parser_TaskAddScheduler", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Scheduler_TaskModifyResource", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Scheduler_TaskAddScheduler", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Scheduler_TaskStatus", stage_builder.STAGE_USE_MIN_TIME),
    ("Saver_Parsercrawldata_TaskModifyResource", stage_builder.STAGE_USE_MIN_TIME),

    ("Selector_SendWorkerRunnable", stage_builder.STAGE_USE_MIN_TIME),

    ("CloudServiceMgt_Apply", stage_builder.STAGE_USE_MIN_TIME),
    ("CloudServiceMgt_ApplyContainerSync", stage_builder.STAGE_USE_MIN_TIME),

    ("IasClient_TaskResult", stage_builder.STAGE_USE_MIN_TIME),
    ("IasClient_ReportTaskBusinessData", stage_builder.STAGE_USE_MIN_TIME),
    ("IasClient_TaskStart", stage_builder.STAGE_USE_MIN_TIME),
    ("taskDataChange", stage_builder.STAGE_USE_MIN_TIME),

    ("Scheduler_TaskReceived", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskAssignedServiceSucceed", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskAssignedServiceFailed", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskWaitReqService", stage_builder.STAGE_USE_MAX_TIME),
    ("Scheduler_TaskStartSend", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskSendResult", stage_builder.STAGE_USE_MIN_TIME,
        lambda s : "Scheduler_TaskSendSucceed" if (s.status() == 0) else "Scheduler_TaskSendFailed",
        ("Scheduler_TaskSendSucceed", "Scheduler_TaskSendFailed")),
    ("Scheduler_TaskStartedInSpider", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskSucceed", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskFailed", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskFinishStatusMsgSent", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_TaskAddAttemptError", stage_builder.STAGE_USE_MIN_TIME,
        lambda s: "Scheduler_TaskReqServiceTimeout" if (s.code() == -903012)
                    else "Scheduler_TaskRestart" if (s.code() == -903016 or s.code() == -903017 or s.code() == -903018)
                        else "Scheduler_TaskOtherError",
        ("Scheduler_TaskReqServiceTimeout", "Scheduler_TaskRestart", "Scheduler_TaskOtherError")),
    ("Scheduler_ServiceRequireFromMgtStart", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_ServiceRequireFromMgtSucceed", stage_builder.STAGE_USE_MIN_TIME),
    ("Scheduler_ServiceRequireFromMgtFailed", stage_builder.STAGE_USE_MIN_TIME)
]


class StageAnalyzer:

    def __init__(self):

        self._fileList = None
        self._taskMap = {}
        self._handleStageCount = 0

        self._builderMap = {}

        for property in STAGE_LIST:
            self._builderMap[property[0]] = stage_builder.Builder(property)

        self._dbEngine = None
        self._tableName = None

    def _connectDb(self):
        #链接数据库，其中有一个圈
        logger.info("start connect DB: %s:%s", constants.MYSQL_HOST, constants.MYSQL_PORT)
        self._dbEngine = sql_engine.SqlEngine(False)
        self._dbEngine.connect(constants.MYSQL_HOST, constants.MYSQL_PORT,
                             constants.MYSQL_ACCOUNT, constants.MYSQL_PASSWORD)
        self._dbEngine.createDB(constants.MYSQL_DB)
        self._dbEngine.useDB(constants.MYSQL_DB)

        logger.info("connect DB SUCCEED, %s:%s", constants.MYSQL_HOST, constants.MYSQL_PORT)


    def _createDbTable(self):
        #创建数据表
        if IS_DEBUG_MODE:
            self._tableName = TABLE_NAME
        else:
            self._tableName = "%s_%d" % (TABLE_NAME, util.unixTime())#unixTime返回的int（time.time()）

        self._dbEngine.dropTable(self._tableName)

        fieldSql = ""
        fieldSql += "task_id CHAR(32) NOT NULL,"
        fieldSql += "app_id CHAR(100),"
        fieldSql += "task_type CHAR(50),"

        for builder in self._builderMap.values():
            fieldSql += builder.createFieldSql()

        fieldSql += "PRIMARY KEY(task_id)"

        #用{}代替原来的语句
        sql = "CREATE TABLE {}({})".format(self._tableName, fieldSql)
        #sql = "CREATE TABLE %s(%s)" % (self._tableName, fieldSql)

        logger.info("start create db table, %s, %s", self._tableName, sql)

        self._dbEngine.execSql(sql)

        logger.info("create db table SUCCEED, %s", self._tableName)

    def _parseStageLine(self, filename, lineNum, line):

        if lineNum <= 1:
            return

        if lineNum % 10000 == 0:
            logger.info("parsing line: %d, %s in file", lineNum, filename)

        self._handleStageCount += 1

        try:
            stageParser = stageM.Stage()

            if not stageParser.parse(line):
                logger.error("parse failed for line: %d, %s in file: %s", lineNum, line, filename)
                return

            if not self._taskMap.has_key(stageParser.taskId()):
                self._taskMap[stageParser.taskId()] = taskM.Task()

            task = self._taskMap[stageParser.taskId()]

            if not self._builderMap.has_key(stageParser.name()):
                logger.warn("not found builder for stage: %s", stageParser.name())
                return

            builder = self._builderMap.get(stageParser.name())
            task.buildStage(stageParser, builder)
        except Exception, e:
            log.logException("parse failed for line: %d, %s in file: %s got exception:" % (lineNum, line, filename,))


    def _parse(self):

        logger.info("start parse")

        for fileName in self._fileList:
            logger.info("start parse file: %s", fileName)
            lineCount = util.parseFileLine(fileName, [self._parseStageLine])
            logger.info("parse done file: %s, line count: %d", fileName, lineCount)

        logger.info("parse SUCCEED")

    def _doWriteDb(self, index, taskIdList, event):

        logger.info("start write to db, index: %d, count: %d", index, len(taskIdList))

        try:
            count = 0
            for taskId in taskIdList:
                self._dbEngine.execSql(self._taskMap.get(taskId).insertSql(self._tableName))
                count += 1
                if count % 400 == 0:
                    logger.info("write to db, index: %d, finished count: %d", index, count)

        except Exception, e:
            log.logException("write to db, index: %d got exception:" % (index,))

        event.set()

        logger.info("write to db SUCCEED, index: %d, count: %d", index, len(taskIdList))

    def _writeToDb(self):

        logger.info("start write to db, total task count: %d", len(self._taskMap))

        eventList = []

        taskIdList = self._taskMap.keys()
        perThreadCount = len(taskIdList) / WRITE_DB_THREAD

        for index in range(WRITE_DB_THREAD):

            event = threading.Event()
            eventList.append(event)

            t = threading.Thread(target=self._doWriteDb,
                                 args=(index, taskIdList[(index * perThreadCount) : ((index + 1) * perThreadCount)], event))
            t.daemon = True
            t.start()

        for event in eventList:
            event.wait()

        logger.info("write to db SUCCEED, total task count: %d", len(self._taskMap))

    def analyze(self, fileList):

        logger.info("start analyze")

        self._fileList = fileList

        self._handleStageCount = 0

        self._connectDb()

        self._createDbTable()

        self._parse()

        self._writeToDb()

        logger.info("analyze SUCCEED, total stage count: %d, total task count: %d", self._handleStageCount, len(self._taskMap))


    def report(self, tableName):

        logger.info("start dump report form table: %s", tableName)

        self._tableName = tableName

        self._connectDb()
        #设置编码格式
        workBook = xlwt.Workbook(encoding='utf-8')
        #添加第一个sheet
        timingSheet = workBook.add_sheet('Timing')
        #设置列名字
        timingSheet.write(0, 0, label='name')
        timingSheet.write(0, 1, label='appId')
        timingSheet.write(0, 2, label='taskType')
        timingSheet.write(0, 3, label='avg')
        timingSheet.write(0, 4, label='min')
        timingSheet.write(0, 5, label='max')
        timingSheet.write(0, 6, label='count')

        reportStageList = [

            #("Saver_Recv_TaskAddResource", "Saver_Recv_TaskModifyResource"),
            #("Saver_Recv_TaskModifyResource", "Saver_Recv_TaskAddScheduler"),
            #("Saver_Recv_TaskAddScheduler", "Saver_Parser_TaskAddResource"),
            #("Saver_Parser_TaskAddResource", "Saver_Parser_TaskModifyResource"),

            ("Saver_Parser_TaskAddResource", "Saver_Parser_TaskAddScheduler"),
            ("Saver_Parser_TaskModifyResource", "Saver_Parser_TaskAddScheduler"),

            ("Saver_Scheduler_TaskModifyResource", "Saver_Scheduler_TaskAddScheduler"),

            ("Saver_Parser_TaskAddScheduler", "Saver_Scheduler_TaskStatus"),
            ("Saver_Scheduler_TaskAddScheduler", "Saver_Scheduler_TaskStatus"),

            #("Saver_Parser_TaskAddScheduler", "Saver_Scheduler_TaskAddScheduler"),

            ("Saver_Parser_TaskAddScheduler", "Selector_SendWorkerRunnable"),
            ("Saver_Scheduler_TaskAddScheduler", "Selector_SendWorkerRunnable"),

            ("Selector_SendWorkerRunnable", "Scheduler_TaskReceived"),

            ("Scheduler_TaskReceived", "Scheduler_TaskAssignedServiceSucceed"),

            ("Scheduler_TaskAssignedServiceSucceed", "Scheduler_TaskStartSend"),
            ("Scheduler_TaskStartSend", "Scheduler_TaskSendSucceed"),

            ("Scheduler_TaskSendSucceed", "Scheduler_TaskStartedInSpider"),
            ("Scheduler_TaskStartedInSpider", "Scheduler_TaskSucceed"),
            ("Scheduler_TaskSendSucceed", "Scheduler_TaskFailed"),

            ("Scheduler_TaskSucceed", "Scheduler_TaskFinishStatusMsgSent"),
            ("Scheduler_TaskFailed", "Scheduler_TaskFinishStatusMsgSent"),

            ("Scheduler_TaskFinishStatusMsgSent", "Saver_Scheduler_TaskStatus"),

            ("Scheduler_TaskReceived", "Scheduler_ServiceRequireFromMgtStart"),
            ("Scheduler_ServiceRequireFromMgtStart", "Scheduler_ServiceRequireFromMgtSucceed"),
            ("Scheduler_ServiceRequireFromMgtStart", "Scheduler_ServiceRequireFromMgtFailed")
        ]

        rowCount = 1
        #先查询汇总的值
        sqlAllFormat = """
          SELECT '{stageEnd} - {stageStart}',
            'ALL', 'ALL',
            AVG({stageEnd} - {stageStart}) as avg,
            MIN({stageEnd} - {stageStart}) as min,
            MAX({stageEnd} - {stageStart}) as max,
            count(*) as count
          FROM {tableName} WHERE {stageEnd} > 0 AND {stageStart} > 0 AND ({stageEnd} - {stageStart}) > 0
        """.replace("{tableName}", tableName)
        #以app_id,task_type为分组依据查询其各种值，因为'{stageEnd} - {stageStart}'有‘’，所以是一个字符串
        sqlGroupByAppTaskFormat = """
          SELECT '{stageEnd} - {stageStart}',
            app_id, task_type,
            AVG({stageEnd} - {stageStart}) as avg,
            MIN({stageEnd} - {stageStart}) as min,
            MAX({stageEnd} - {stageStart}) as max,
            count(*) as count
          FROM {tableName} WHERE {stageEnd} > 0 AND {stageStart} > 0 AND ({stageEnd} - {stageStart}) > 0
          GROUP BY app_id, task_type
        """.replace("{tableName}", tableName)

        for (stageStart, stageEnd) in reportStageList:

            sqlAll = sqlAllFormat.replace("{stageStart}", stageStart).replace("{stageEnd}", stageEnd)

            logger.info("timing query sqlAll:\n%s", sqlAll)

            for d in self._dbEngine.execSql(sqlAll).fetchall():
                columnCount = 0
                for v in d:
                    timingSheet.write(rowCount, columnCount, v)
                    columnCount += 1

                rowCount += 1

            sqlGroupByAppTask = sqlGroupByAppTaskFormat.replace("{stageStart}", stageStart).replace("{stageEnd}", stageEnd)

            logger.info("timing query sqlGroupByAppTask:\n%s", sqlGroupByAppTask)

            for d in self._dbEngine.execSql(sqlGroupByAppTask).fetchall():
                columnCount = 0
                for v in d:
                    timingSheet.write(rowCount, columnCount, v)
                    columnCount += 1

                rowCount += 1

        #创建sheet名
        unScheduleSheet = workBook.add_sheet('UnSchedule')
        #给每一列取列名
        unScheduleSheet.write(0, 0, label='name')
        unScheduleSheet.write(0, 1, label='appId')
        unScheduleSheet.write(0, 2, label='taskType')
        unScheduleSheet.write(0, 3, label='count')

        #这是失败的条件，若可以更新的话，可以从外部的csv文件读取（思路）
        unScheduleCheckList = [
            ("UnScheduleNewParseTask", "Saver_Parser_TaskAddResource > 0 AND Saver_Parser_TaskAddScheduler <= 0"),
            ("UnScheduleOldParseTask", "Saver_Parser_TaskModifyResource > 0 AND Saver_Parser_TaskAddScheduler <= 0"),
            ("UnSelectParseTask", "Saver_Parser_TaskAddScheduler > 0 AND Selector_SendWorkerRunnable <= 0"),

            ("UnScheduleRingTask", "Saver_Scheduler_TaskModifyResource > 0 AND Saver_Scheduler_TaskAddScheduler <= 0"),
            ("UnSelectRingTask", "Saver_Scheduler_TaskAddScheduler > 0 AND Selector_SendWorkerRunnable <= 0"),
        ]

        rowCount = 1

        unScheduleAllSqlFormat = """
          SELECT '{name}', 'ALL', 'ALL', count(*)
          FROM {tableName} WHERE {condition}
        """.replace("{tableName}", tableName)

        unScheduleAppTaskSqlFormat = """
          SELECT '{name}', app_id, task_type, count(*)
          FROM {tableName} WHERE {condition}
          GROUP BY app_id, task_type
        """.replace("{tableName}", tableName)

        for (name, condition) in unScheduleCheckList:
            sqlAll = unScheduleAllSqlFormat.replace("{name}", name).replace("{condition}", condition)

            logger.info("un-schedule query sqlAll:\n%s", sqlAll)

            for d in self._dbEngine.execSql(sqlAll).fetchall():
                columnCount = 0
                for v in d:
                    unScheduleSheet.write(rowCount, columnCount, v)
                    columnCount += 1

                rowCount += 1

            sqlGroupByAppTask = unScheduleAppTaskSqlFormat.replace("{name}", name).replace("{condition}", condition)

            logger.info("un-schedule query sqlGroupByAppTask:\n%s", sqlGroupByAppTask)

            for d in self._dbEngine.execSql(sqlGroupByAppTask).fetchall():
                columnCount = 0
                for v in d:
                    unScheduleSheet.write(rowCount, columnCount, v)
                    columnCount += 1

                rowCount += 1

        workBook.save('report.xls')

        logger.info("dump report SUCCEED")


def main():
    #fileList是需要完成的csv文件列表
    fileList = [
         "/Users/apple/Desktop/t_data/stage_saver_1.csv",
         "/Users/apple/Desktop/t_data/stage_saver_2.csv",
         "/Users/apple/Desktop/t_data/stage_saver_3.csv",
         "/Users/apple/Desktop/t_data/stage_saver_4.csv",
         "/Users/apple/Desktop/t_data/stage_saver_5.csv",
         "/Users/apple/Desktop/t_data/stage_saver_6.csv",
         "/Users/apple/Desktop/t_data/stage_saver_7.csv",
         "/Users/apple/Desktop/t_data/stage_saver_8.csv"
     ]

    StageAnalyzer().analyze(fileList)

    StageAnalyzer().report("stage_analyzer_1553938851")

if __name__ == '__main__':
    main()