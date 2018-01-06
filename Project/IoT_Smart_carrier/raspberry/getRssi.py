import pymysql
import serial
import threading, time


#
#       Connect To MySQL
#
def connectDB():
        conn = pymysql.connect(
                host = '192.168.0.6',
                user = 'root',
                password = 'qwerty1',
                db = 'carrier')

        return conn;

#
#       Receive RSSI Data From Database
#
#       return: RSSI value
#
def getRssi():
        conn = connectDB()
        try:
                with conn.cursor() as cursor:
                        sql = "SELECT rssi FROM carrier_info"
                        cursor.execute(sql)
                        value = cursor.fetchone()

        finally:
                return int(value[0])
