#-*-coding:utf-8-*-

###
### simple logger
### by raphyer

import os

import ctypes

import logging
import logging.config
import random

from io import open

from cloghandler import ConcurrentRotatingFileHandler

import util

BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE = range(8)

COLORS = {
    'WARNING'  : MAGENTA,
    'INFO'     : GREEN,
    'DEBUG'    : WHITE,
    'CRITICAL' : RED,
    'ERROR'    : RED,
    'RED'      : RED,
    'GREEN'    : GREEN,
    'YELLOW'   : YELLOW,
    'BLUE'     : BLUE,
    'MAGENTA'  : MAGENTA,
    'CYAN'     : CYAN,
    'WHITE'    : WHITE,
}

RESET_SEQ = "\033[0m"
COLOR_SEQ = "\033[%dm"
BOLD_SEQ  = "\033[1m"


##
## windows color
##

WINS_FOREGROUND_GREEN = 0x0a
WINS_FOREGROUND_BLUE = 0x09
WINS_FOREGROUND_RED = 0x0c
WINS_FOREGROUND_PINK = 0x0d
WINS_FOREGROUND_WHITE = 0x0f

WIN_COLORS = {
    'WARNING': WINS_FOREGROUND_PINK,
    'INFO': WINS_FOREGROUND_GREEN,
    'DEBUG': WINS_FOREGROUND_WHITE,
    'CRITICAL': WINS_FOREGROUND_RED,
    'ERROR': WINS_FOREGROUND_RED,
}

OS_TYPE = util.osType()

class UTColorFormatter(logging.Formatter):

    def __init__(self, *args, **kwargs):
        # can't do super(...) here because Formatter is an old school class
        logging.Formatter.__init__(self, *args, **kwargs)
        if (OS_TYPE == util.OS_WINDOWS):
            self._sysOutHandler = ctypes.windll.kernel32.GetStdHandle(-11)

    def _doFormatInPosix(self, record):
        levelname = record.levelname
        color     = COLOR_SEQ % (90 + COLORS[levelname])
        message   = logging.Formatter.format(self, record)
        message   = message.replace("$RESET", RESET_SEQ)\
                           .replace("$BOLD",  BOLD_SEQ)\
                           .replace("$COLOR", color)
        for k,v in COLORS.items():
            message = message.replace("$" + k,    COLOR_SEQ % (v+90))\
                             .replace("$BG" + k,  COLOR_SEQ % (v+40))\
                             .replace("$BG-" + k, COLOR_SEQ % (v+40))
        return message + RESET_SEQ

    def _setWinsTextColor(self, color):
        ctypes.windll.kernel32.SetConsoleTextAttribute(self._sysOutHandler, color)

    def resetWinsTextColor(self):
        self._setWinsTextColor(WINS_FOREGROUND_RED | WINS_FOREGROUND_GREEN | WINS_FOREGROUND_BLUE)

    def _doFormatInWindows(self, record):
        levelname = record.levelname
        color     = WIN_COLORS[levelname]
        message   = logging.Formatter.format(self, record)
        message = message.replace("$RESET", "") \
            .replace("$BOLD", "") \
            .replace("$COLOR", "")

        for k, v in COLORS.items():
            message = message.replace("$" + k, "") \
                .replace("$BG" + k, "") \
                .replace("$BG-" + k, "")

        self._setWinsTextColor(color)

        return message

    def format(self, record):

        if (OS_TYPE == util.OS_POSIX):
            return self._doFormatInPosix(record)

        if (OS_TYPE == util.OS_WINDOWS):
            return self._doFormatInWindows(record)

        return self._doFormatInPosix(record)


class UTStreamHandler(logging.StreamHandler):
    def emit(self, record):
        logging.StreamHandler.emit(self, record)
        if ((OS_TYPE == util.OS_WINDOWS) and isinstance(self.formatter, UTColorFormatter)):
            self.formatter.resetWinsTextColor()

_hookFun = None

class HookStreamHandler(logging.StreamHandler):

    # "WARNING",
    HOOK_LEVELS = ["ERROR", "CRITICAL"]

    def emit(self, record):
        if _hookFun and (record.levelname in HookStreamHandler.HOOK_LEVELS):
            _hookFun(record.levelname, self.formatter.format(record))


logging.UTColorFormatter = UTColorFormatter
logging.UTStreamHandler = UTStreamHandler
logging.ConcurrentRotatingFileHandler = ConcurrentRotatingFileHandler
logging.HookStreamHandler = HookStreamHandler

_isInited = False

def init(cfgFileName):

    global _isInited
    _isInited = True

    logging.config.fileConfig(cfgFileName)

    print "-------- log config path: " + cfgFileName

def initWithDefult(defFile, projectName):

    dFile = open(defFile)
    content = dFile.read()
    dFile.close()

    filePath = os.path.dirname(defFile)

    logDir = os.path.join(filePath[:(filePath.rindex(os.sep))], "log")
    util.createDir(logDir)

    content = content.replace("./defaultX.log", os.path.join(logDir, projectName + ".log"))

    tmpFileName = os.path.join(filePath,
                               projectName + "_tmp_" + str(util.unixTime()) + "_" + str(random.randint(1, 10000)) + ".log")
    tmpFile = open(tmpFileName, mode="w+")
    tmpFile.write(content)
    tmpFile.close()
    init(tmpFileName)
    os.remove(tmpFileName)


def getLogger(name):

    if not _isInited:
        scriptPath = os.path.split(os.path.realpath(__file__))[0]
        init(os.path.join(scriptPath[:(scriptPath.rindex((os.sep + "src")))], "config", "default.log.config"))

    return logging.getLogger(name)


###
### fun -> def fun(logLevel, logMsg)
###
def hookImportantMsg(fun):
    global _hookFun
    _hookFun = fun

def logException(msg):

    if _hookFun:
        _hookFun("CRITICAL", msg)

    if (OS_TYPE == util.OS_WINDOWS):
        sysOutHandler = ctypes.windll.kernel32.GetStdHandle(-11)
        ctypes.windll.kernel32.SetConsoleTextAttribute(sysOutHandler, util.CONSOLE_RED_COLOR)
        logging.exception(msg)
        ctypes.windll.kernel32.SetConsoleTextAttribute(sysOutHandler, util.CONSOLE_DEF_COLOR)
    else:
        logging.exception(util.CONSOLE_RED_COLOR + msg + util.CONSOLE_DEF_COLOR)

if __name__ == '__main__':
    pass
