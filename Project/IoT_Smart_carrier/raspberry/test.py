import rcvRSS as rcv
import sys, tty, termios, os
import motorCtl as HBridge
import threading
import time

speedLeft = 0
speedRight = 0
RSSI = 0

def updateRSSI():
        while(1):
                global RSSI
                print("Wait...")
                RSSI = rcv.getRssi()
#               RSSI = input()
                print(RSSI)
                updatePos()
                time.sleep(1)


def updatePos():
        print('Current RSSI: ', RSSI)

        if(RSSI < -80 ):
                print("Go")
                speedleft = 1.0
                speedright = 1.0

                HBridge.setMotorLeft(speedleft)
                HBridge.setMotorRight(speedright)


        if(-70 > RSSI and RSSI >= -80):
                print("SLOW")
                speedleft = 0.5
                speedright = 0.5

                HBridge.setMotorLeft(speedleft)
                HBridge.setMotorRight(speedright)

        if(RSSI > -70):
                print("STOP")
                speedleft = 0
                speedright = 0

                HBridge.setMotorLeft(speedleft)
                HBridge.setMotorRight(speedright)


t1 = threading.Thread(target=updateRSSI, args=())
t1.start()
