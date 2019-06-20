#-*-coding:utf-8-*-

###
### utility module
### by raphyer

import os
import re
import time
import datetime
import dateutil.parser
import threading
import ctypes
import bisect
import getopt
import json
import signal
import hashlib
import zipfile
from io import open
from functools import wraps


(OS_POSIX, OS_WINDOWS, OS_NONE) = range(0, 3)


def osType():
    if (os.name == 'posix'):
        return OS_POSIX

    if (os.name == 'nt'):
        return OS_WINDOWS

    print "------------------------ Unknown os.name: " + os.name
    return OS_NONE


if (osType() == OS_WINDOWS):

    CONSOLE_BLACK_COLOR   = 0x00
    CONSOLE_RED_COLOR     = 0x0c
    CONSOLE_GREEN_COLOR   = 0x0a
    CONSOLE_YELLOW_COLOR  = 0x0e
    CONSOLE_BLUE_COLOR    = 0x09
    CONSOLE_PURPLE_COLOR  = 0x0d
    CONSOLE_WHITE_COLOR   = 0x0f

    CONSOLE_DEF_COLOR = (CONSOLE_RED_COLOR | CONSOLE_GREEN_COLOR | CONSOLE_BLUE_COLOR)

    gSysOutHandler = ctypes.windll.kernel32.GetStdHandle(-11)

else:
    CONSOLE_BLACK_COLOR   = "\033[90m"
    CONSOLE_RED_COLOR     = "\033[91m"
    CONSOLE_GREEN_COLOR   = "\033[92m"
    CONSOLE_YELLOW_COLOR  = "\033[93m"
    CONSOLE_BLUE_COLOR    = "\033[94m"
    CONSOLE_PURPLE_COLOR  = "\033[95m"
    CONSOLE_WHITE_COLOR   = "\033[97m"

    CONSOLE_DEF_COLOR = CONSOLE_WHITE_COLOR


def printColorFulString(color, msg):
    if (osType() == OS_WINDOWS):
        ctypes.windll.kernel32.SetConsoleTextAttribute(gSysOutHandler, color)
        print msg
        ctypes.windll.kernel32.SetConsoleTextAttribute(gSysOutHandler, CONSOLE_DEF_COLOR)
    else:
        print (color + msg + CONSOLE_DEF_COLOR)


def _isFlieMatch(fileName, includeList, excludeList):

    if ((not includeList) and (not excludeList)):
        return True

    if (includeList):
        found = False
        for restr in includeList:
            pattern = re.compile(restr)
            if (pattern.match(fileName)) :
                found = True
                break

        if (not found) :
            return False

    if (excludeList):
        for restr in excludeList:
            pattern = re.compile(restr)
            if (pattern.match(fileName)) :
                return False

    return True


def getFileList(path, includeList = None, excludeList = None):

    fileList = []

    files = os.listdir(path)

    for f in files:

        if(os.path.isdir(os.path.join(path, f))):

            if(f[0] == '.'):
                continue
            else:
                subFileList = getFileList(os.path.join(path, f), includeList, excludeList)
                fileList.extend(subFileList)

        if(os.path.isfile(path + '/' + f)):
            if (_isFlieMatch(f, includeList, excludeList)):
                fileList.append(os.path.join(path, f))

    return fileList


def unixTime():
    return int(time.time())

def unixTimeInMS():
    return int(time.time() * 1000)

def datetimeToUnixTime(d):
    return time.mktime(d.timetuple()) + (d.microsecond / 1000000.0)

def diffSecondesToUTC() :
    curTime = unixTime()
    utcTime = datetimeToUnixTime(dateutil.parser.parse(isoTimeString(curTime, True)))
    return int(curTime - utcTime)

def timeStringToUnixTime(dstr, isUTC=False):
    if isUTC:
        return datetimeToUnixTime(dateutil.parser.parse(dstr)) + diffSecondesToUTC()
    else:
        return datetimeToUnixTime(dateutil.parser.parse(dstr))

def timeStringToUnixTimeInMili(dstr):
    return datetimeToUnixTime(dateutil.parser.parse(dstr)) * 1000

def unixTimeToDatetime(t):
    return datetime.datetime.fromtimestamp(t)


### t is local time
def isoTimeString(t, toUTC = False):

    if not isinstance(t, datetime.datetime):
        if toUTC:
            t = datetime.datetime.utcfromtimestamp(t)
        else:
            t = datetime.datetime.fromtimestamp(t)
    else:
        if toUTC:
            t = datetime.datetime.utcfromtimestamp(datetimeToUnixTime(t))

    return t.isoformat()


_sgUniqueId = 0
_sgUniqueIdLock = threading.Lock()

def getUniqueId():

    global _sgUniqueId
    global _sgUniqueIdLock

    with _sgUniqueIdLock:
        tmp = _sgUniqueId
        _sgUniqueId += 1
        return tmp


def createDir(path):
    if os.path.exists(path):
        return
    os.makedirs(path)


def parseInt(data, defVal = -1):
    try :
        return long(data)
    except Exception, e:
        return defVal

def split(s, sep = ",") :
    ret = s.split(sep)
    return [name.strip() for name in ret]

###
### the value reaturn keyFun(d) can be used in "==" to compare it's value
### like int/float/string
###
def bsearch(array, val, keyFun) :
    keys = [keyFun(d) for d in array]
    index = bisect.bisect_left(keys, keyFun(val))
    if index != len(array) and keyFun(array[index]) == keyFun(val):
        return index
    return -index


def findSlice(s, startStr, endStr):
    startIndex = s.find(startStr)
    if (startIndex == -1):
        return (-1, -1)

    endIndex = (s[(startIndex + 1):]).find(endStr)
    if (endIndex == -1):
        return (-1, -1)

    return (startIndex, startIndex + 1 + endIndex)


def replaceInsideString(s, oldStr, newStr):

    ret = s
    loopIndex = 0
    while True:
        (startIndex, endIndex) = findSlice(ret[loopIndex:], "'", "'")
        if startIndex < 0:
            (startIndex, endIndex) = findSlice(ret[loopIndex:], '"', '"')

        if startIndex < 0:
            break

        startIndex += loopIndex
        endIndex += loopIndex

        tmpStr = ret[startIndex:(endIndex + 1)].replace(oldStr, newStr)
        ret = ret[:startIndex] + tmpStr + ret[(endIndex + 1):]
        loopIndex = startIndex + len(tmpStr)

    return ret


def getopts(args, optString):

    options, remain = getopt.getopt(args, optString)

    ops = {}
    for (k, v) in options:
        ops[k] = v

    return ops

def strEnclosed(s, startStr, endStr):

    startIndex = s.find(startStr)
    if startIndex == -1:
        return None

    startIndex += len(startStr)

    endIndex = s[startIndex:].find(endStr)
    if endIndex == -1:
        return None

    endIndex = startIndex + endIndex

    return s[startIndex:endIndex]

def getMdian(dataList):
    dataList.sort()
    half = len(dataList) // 2
    return (dataList[half] + dataList[~half]) / 2

def getKnownOpts(args, optString) :

    newOptList = optString

    while True:
        try:
            options, remain = getopt.getopt(args, newOptList)
            ops = {}
            for (k, v) in options:
                ops[k] = v
            return ops
        except getopt.GetoptError, e:
            unknownOption = strEnclosed(str(e), "option", "not recognized")
            if not unknownOption:
                raise e
            unknownOption = unknownOption.strip()
            if (len(unknownOption) == 2) and unknownOption[0] == "-" :
                for index in range(len(args)):
                    if args[index] == unknownOption:
                        newOptList = newOptList + unknownOption[1]
                        if index < len(args) - 1 and args[index + 1][0] != "-" :
                            newOptList = newOptList + ":"
                        break
                continue
            raise e



def _byteify(input):
    if isinstance(input, dict):
        return {_byteify(key): _byteify(value)
                for key, value in input.iteritems()}
    elif isinstance(input, list):
        return [_byteify(element) for element in input]
    elif isinstance(input, unicode):
        return input.encode('utf-8')
    else:
        return input

def loadJsonFromFile(fullFileName, encoding='utf-8', byteify=True) :
    f = open(fullFileName, encoding="utf-8")
    ret = json.load(f, encoding=encoding)
    if byteify:
        ret = _byteify(ret)
    f.close()
    return ret

def loadJson(s, encoding='utf-8', byteify=True) :
    ret = json.loads(s, encoding=encoding)
    if byteify:
        ret = _byteify(ret)
    return ret

def writeJsonToFile(fullFileName, d, indent=None, encoding='utf-8'):
    f = open(fullFileName, encoding=encoding, mode="w+")
    f.write(json.dumps(d, indent=indent))
    f.close()


def toJsonString(d):
    return json.dumps(d)

def _defEqual(d1, d2) :
    return d1 == d2

### find element that in l1 but not in l2
def diff(l1, l2, isElementEqual = None) :

    if not isElementEqual:
        isElementEqual = _defEqual

    ret = []
    for d1 in l1:
        found = False
        for d2 in l2:
            if isElementEqual(d1, d2):
                found = True
                break
        if not found:
            ret.append(d1)
    return ret


def ip():
    try :
        import socket
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(("8.8.8.8", 80))
        ip = s.getsockname()[0]
        s.close()
        return ip
    except Exception:
        return ""

def parserIp(ipString):

    "ipv4:127.0.0.1:51282:"
    match = re.search(r"ipv4:([\d.]+):\d+", ipString.strip())
    if match:
        return match.group(1)
    print "--%s--" % (ipString.strip(),)

    ## TODO ipv6

    return ipString.strip()

def genToken(salt, randomLen=4) :

    step1 = hashlib.sha1(str(unixTimeInMS()) + salt + "!oy@#_&%4;" + str(os.urandom(randomLen))).hexdigest()
    return hashlib.md5(step1 + str(os.urandom(randomLen))).hexdigest()


def timeProfile(printFun=None):

    def decorate(func):

        @wraps(func)
        def wrapper(*args, **kwargs):
            start = unixTimeInMS()
            result = func(*args, **kwargs)
            end = unixTimeInMS()
            if printFun:
                printFun("[PROFILE] %s::%s use time: %d" % (func.__module__, func.__name__, end - start))
            else:
                print("[PROFILE] %s::%s use time: %d" % (func.__module__, func.__name__, end - start))
            return result
        return wrapper

    return decorate


def zipFile(outputZipFileName, zipFileList) :

    f = zipfile.ZipFile(outputZipFileName, "w", zipfile.ZIP_DEFLATED)
    for fileName in zipFileList:
        f.write(fileName, os.path.basename(fileName))
    f.close()


def md5(strContent):
    return hashlib.md5(strContent).hexdigest()

def md5File(fileName):
    f = open(fileName, mode="rb")
    fileMd5 = md5(f.read())
    f.close()
    return fileMd5

###
### hang and unhang main thread
###

_event = threading.Event()

def hangMainThread():

    ### make the ctrl-c work
    def _exit(signum, frame):
        print('Exit with %s, %s' % (signum, frame))
        _event.set()
        exit(0)

    signal.signal(signal.SIGINT, _exit)
    signal.signal(signal.SIGTERM, _exit)
    signal.pause()

    _event.wait()
    exit(0)

def unhangMainThread():
    signal.alarm(1)
    _event.set()

def parseFileLine(fileName, callbackList):
    count = 0
    f = open(fileName, "r+")
    for line in f.readlines():
        count += 1
        for callback in callbackList:
            callback(fileName, count, line)
    return count


def stringInside(s, startStr, endStr):
    startIndex = s.find(startStr)
    if (startIndex == -1):
        return s

    endIndex = (s[(startIndex + len(startStr)):]).find(endStr)
    if (endIndex == -1):
        return s
    return s[(startIndex + len(startStr)):(startIndex + len(startStr) + endIndex)]

def findAndUpdate(s, searchS):
    index = s.find(searchS)
    if index == -1:
        return None

    return s[(index + len(searchS)): -1]


if __name__ == '__main__':

    t = unixTime()
    print isoTimeString(t)
    print isoTimeString(t, True)

    d = unixTimeToDatetime(t)
    print isoTimeString(d)
    print isoTimeString(d, True)

    print diffSecondesToUTC()

    # ori = " 'a  b c'\" 1  2 \"  "
    # afterReplaced = replaceInsideString(ori, " ", "xx")
    # assert afterReplaced == " 'axxxxbxxc'\"xx1xxxx2xx\"  "
    # re = replaceInsideString(afterReplaced, "xx", " ")
    # assert ori == re
    #
    # assert diff([1, 2, 3, 4], [2, 3]) == [1, 4]
    # assert diff([2, 3], [2, 3]) == []
    # assert diff([2, 3], [1, 2, 3, 4]) == []

    #print bsearch([(0, 0), (1, 1), (3, 1), (4, 4), (5, 5)], (2, 2), keyFun=lambda r:r[1])
    #print getFileList("/Users/apple/Desktop/work", [r"\w*\.py$"], [r"^__init__.py$"])

