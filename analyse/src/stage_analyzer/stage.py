#-*-coding:utf-8-*-

###
### stage parser
### by raphyer


import os,sys
sys.path.append(sys.path[0][:(sys.path[0].rindex((os.sep + "src")))])

import src.utils.util as util
import src.utils.log
logger = src.utils.log.getLogger("root")

# "March 27th 2019, 16:00:00.012",1b8e5379db5946ba9a1a159046c487d8,"com.duowan.mobile 7.11.1",INFO,0,"Saver_Scheduler_TaskStatus",
# "March 27th 2019, 16:00:00.024",e6c3a37d76bf4b788c9e55924dec2811,"com.duowan.mobile 7.11.1",INFO,,"Scheduler_TaskFailed",

ELEMENT_INDEX_TIME = 0
ELEMENT_INDEX_TASK_ID = 1
ELEMENT_INDEX_APP_ID = 2
ELEMENT_INDEX_TASK_TYPE = 3
ELEMENT_INDEX_STATUS = 4
ELEMENT_INDEX_NAME = 5
ELEMENT_INDEX_T_APP_ID = 6
ELEMENT_INDEX_COUNT = 7

class Stage:

    def __init__(self):

        self._occurTime = None
        self._name = None
        self._taskId = None
        self._appId = None
        self._taskType = None
        self._status = None
        self._code = None

    def _doParseElement(self, line):

        elementList = []

        while len(line) > 0:

            if line[0] == '"':
                endIndex = line[1:].find('"')
                if endIndex == -1:
                    logger.error("bad data in line for end quota not found, %s", line)
                    return None
                element = line[1:endIndex + 1]
                elementList.append(element)
                line = line[endIndex + 1 + 1:]

                continue

            if line[0] == ",":
                endIndex = line[1:].find(',')

                if endIndex != -1:
                    element = line[1:endIndex + 1]
                    line = line[endIndex + 1:]
                else:
                    element = line[1:]
                    line = ""

                elementList.append(element)

                continue

            logger.error("bad data in line for not sep found, %s", line)
            return None

        return elementList


    def parse(self, line):

        elementList = self._doParseElement(line)

        if elementList is None:
            return False

        logger.debug("found elementList: %s for line: %s", str(elementList), line)

        if len(elementList) != ELEMENT_INDEX_COUNT:
            logger.error("parse bad size of elementList: %s for line: %s", str(elementList), line)
            return False

        self._occurTime = long(util.timeStringToUnixTimeInMili(elementList[ELEMENT_INDEX_TIME]))
        self._name = util.stringInside(elementList[ELEMENT_INDEX_NAME], '"', '"')
        self._taskId = elementList[ELEMENT_INDEX_TASK_ID]
        self._appId = elementList[ELEMENT_INDEX_APP_ID] if elementList[ELEMENT_INDEX_APP_ID] else elementList[ELEMENT_INDEX_T_APP_ID]
        # print  self._appId, elementList[ELEMENT_INDEX_APP_ID], elementList[ELEMENT_INDEX_T_APP_ID]
        self._appId = util.stringInside(self._appId, '"', '"')
        self._taskType = util.stringInside(elementList[ELEMENT_INDEX_TASK_TYPE], '"', '"')
        self._status = util.parseInt(elementList[ELEMENT_INDEX_STATUS], -1)

        # print "FFF", self._occurTime, self._name, self._taskId, self._appId, self._taskType

        if not self._occurTime or not self._name or not self._taskId or not self._appId:
            logger.error("invalid element in elementList: %s for line: %s", str(elementList), line)
            return False

        return True

    def occurTime(self):
        return self._occurTime

    def name(self):
        return self._name

    def taskId(self):
        return self._taskId

    def appId(self):
        return self._appId

    def taskType(self):
        return self._taskType

    def status(self):
        return self._status

    def code(self):
        return self._code
