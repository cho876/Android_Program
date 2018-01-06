import RPi.GPIO as io
io.setmode(io.BCM)

PWM_MAX = 100

io.setwarnings(False)

leftMotor_DIR_pin = 23
io.setup(leftMotor_DIR_pin, io.OUT)

rightMotor_DIR_pin = 18
io.setup(rightMotor_DIR_pin, io.OUT)

io.output(leftMotor_DIR_pin, False)

io.output(rightMotor_DIR_pin, False)

leftMotor_PWM_pin = 22
rightMotor_PWM_pin = 17

io.setup(leftMotor_PWM_pin, io.OUT)
io.setup(rightMotor_PWM_pin, io.OUT)

leftMotorPWM = io.PWM(leftMotor_PWM_pin, 20)
rightMotorPWM = io.PWM(rightMotor_PWM_pin, 20)

leftMotorPWM.start(0)
leftMotorPWM.ChangeDutyCycle(0)

rightMotorPWM.start(0)
rightMotorPWM.ChangeDutyCycle(0)

leftMotorPower = 0
rightMotorPower = 0

def getMotorPowers():
        return (leftMotorPower, rightMotorPower)

def setMotorLeft(power):
        if power < 0:
                io.output(rightMotor_DIR_pin, True)
                pwm = -int(PWM_MAX * power)
                if pwm > PWM_MAX:
                        pwm = PWM_MAX
        elif power > 0:
                io.output(rightMotor_DIR_pin, False)
                pwm = int(PWM_MAX * power)
                if pwm > PWM_MAX:
                        pwm = PWM_MAX
        else:
                # Stopp mode for the right motor
                io.output(rightMotor_DIR_pin, False)
                pwm = 0
        leftMotorPower = pwm
        leftMotorPWM.ChangeDutyCycle(pwm)

def setMotorRight(power):

        if power < 0:
                # Reverse mode for the right motor
                io.output(rightMotor_DIR_pin, True)
                pwm = -int(PWM_MAX * power)
                if pwm > PWM_MAX:
                        pwm = PWM_MAX
        elif power > 0:
                # Forward mode for the right motor
                io.output(rightMotor_DIR_pin, False)
                pwm = int(PWM_MAX * power)
                if pwm > PWM_MAX:
                        pwm = PWM_MAX
        else:
                # Stop mode for the right motor
                io.output(rightMotor_DIR_pin, False)
                pwm = 0

        #   print "SetMotorRight", pwm
        rightMotorPower = pwm
        rightMotorPWM.ChangeDutyCycle(pwm)

def exit():
        # Program will clean up all GPIO settings and terminates
        io.output(leftMotor_DIR_pin, False)
        io.output(rightMotor_DIR_pin, False)
        io.cleanup