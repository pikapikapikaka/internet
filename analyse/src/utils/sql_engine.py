#-*-coding:utf-8-*-

###
### sql engine
### by raphyer

import sqlalchemy
import sqlalchemy.orm

class SqlEngine():

    def __init__(self, isDebug):
        self._isDebug = isDebug
        self._engine = None
        self._mainSession = None
        self._DBSession = None
        self._ScopedSession = None

        self._ip = None
        self._port = None
        self._user = None
        self._password = None


    def connect(self, ip, port, user, password, dbName=None):

        self._ip = ip
        self._port = port
        self._user = user
        self._password = password

        #组成链接数据库的格式，一个引擎，会话将要使用该引擎
        if dbName :
            protocol = 'mysql+pymysql://%s:%s@%s:%s/%s?charset=utf8' % (user, password, ip, port, dbName)
        else :
            protocol = 'mysql+pymysql://%s:%s@%s:%s' % (user, password, ip, port)

        #连接数据库
        self._engine = sqlalchemy.create_engine(protocol, encoding='utf-8', echo=self._isDebug, isolation_level="READ UNCOMMITTED")

        if dbName:
            #建立会话会话类
            self._DBSession = sqlalchemy.orm.sessionmaker(bind=self._engine)
            #保护线程安全，类似于单例模式，如果有session就返回，默认的是产生的两个session不是一样的
            self._ScopedSession = sqlalchemy.orm.scoped_session(self._DBSession)
            #mainSession为执行的会话
            self._mainSession = self._ScopedSession()

    def createDB(self, dbName):
        self._engine.execute("CREATE DATABASE IF NOT EXISTS %s DEFAULT CHARSET utf8 COLLATE utf8_general_ci" % (dbName,))

    def useDB(self, dbName):
        self._engine.execute("USE %s" % (dbName,))
        self.connect(self._ip, self._port, self._user, self._password, dbName)

    def isTableExist(self, tableName):
        return  self._engine.dialect.has_table(self._engine, tableName)

    def execSql(self, sql):
        return self._engine.execute(sql)

    ### main thread-safe session
    def mainSession(self):
        return self._mainSession

    ### create NOT thread-safe session
    def createSession(self):
        return self._DBSession()

    def dropTable(self, tableName):
        self._mainSession.execute('DROP TABLE IF EXISTS %s' % (tableName,))

