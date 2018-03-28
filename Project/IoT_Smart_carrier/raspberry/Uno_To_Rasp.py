import threading
import time
import serial

SER = serial.Serial('COM5', 9600)   # Connect Iduino UNO
TIMER = 0.5     # Thread timer time


class Sensor(threading.Thread):
    timer = 0

    def __init__(self, timer):
        threading.Thread.__init__(self)
        self.timer = timer

    # Data parsing
    @classmethod
    def parsetodata(self, value):
        data_units = value.split(',')
        print("Sensor: "+data_units[0])     # Ultrasonic wave
        print("Weight: "+data_units[1])     # Weight


    # Thread execution in "TIMER" sec
    def run(self):
        while (True):
            value = SER.readline()
            self.parsetodata(value)
            time.sleep(self.timer)


def Main():
    sensorData = Sensor(TIMER)
    sensorData.run()

if __name__ == '__main__':
    Main()