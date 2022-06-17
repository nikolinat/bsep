import glob
import os


class Device:
    def __init__(self, id, name, type, period):
        self.id = id
        self.name = name
        self.type = type
        self.period = period


def readDevice(devices, line):
    splitted = line.split(',')
    device = Device(splitted[0], splitted[2], splitted[3], splitted[4])
    devices[device.id] = device


def read(type):
    devices = {}
    path = '../src/main/java/files/config/'
    for filename in glob.glob(os.path.join(path, '*.txt')):
        with open(os.path.join(os.getcwd(), filename), 'r') as f:
            lines = f.readlines()
            for line in lines:
                if type in line:
                    readDevice(devices, line)
    return devices
