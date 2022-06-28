import base64
import datetime
import glob
import os
import random
import time

import requests
from Crypto.Cipher import AES

key = "ThisIsA16ByteKey"


class Device:
    def __init__(self, id, name, type, period, real_estate_id):
        self.id = id
        self.name = name
        self.type = type
        self.period = period
        self.real_estate_id = real_estate_id


def readDevice(devices, line, real_estate_id):
    splitted = line.split(',')
    device = Device(splitted[0], splitted[2], splitted[3], splitted[4], real_estate_id)
    devices[device.id] = device


def read(type):
    devices = {}
    path = '../src/main/java/files/config/'
    for filename in glob.glob(os.path.join(path, '*.txt')):
        real_estate_id = filename.split("\\")[1].split(".txt")[0]
        with open(os.path.join(os.getcwd(), filename), 'r') as f:
            lines = f.readlines()
            for line in lines:
                if type in line:
                    readDevice(devices, line, real_estate_id)
    return devices


def pad(byte_array):
    BLOCK_SIZE = 16
    pad_len = BLOCK_SIZE - len(byte_array) % BLOCK_SIZE
    return byte_array + (bytes([pad_len]) * pad_len)


def encrypt(key, message):
    byte_array = message.encode("UTF-8")

    padded = pad(byte_array)

    iv = os.urandom(AES.block_size)
    cipher = AES.new(key.encode("UTF-8"), AES.MODE_CBC, iv)
    encrypted = cipher.encrypt(padded)
    return base64.b64encode(iv + encrypted).decode("UTF-8")


def state(devices, messages):
    while 1:
        for k, v in devices.items():
            message = random.choice(messages)
            value = 0
            if v.type == 'HEATING' and message == 'Heating is on':
                value = random.randrange(15, 30)
            if v.type == 'AIR_CONDITIONING' and message == 'Air conditioning is in cooling mode' or \
                    v.type == 'AIR_CONDITIONING' and message == 'Air conditioning is in heating mode':
                value = random.randrange(15, 30)
            requests.post('https://localhost:8444/api/v1/device/state',
                          verify='../src/main/java/files/keystores/root.cer', json={
                    'realEstateId': v.real_estate_id,
                    'dateTime': datetime.datetime.utcnow().isoformat(),
                    'id': v.id,
                    'type': v.type,
                    'message': encrypt(key, message),
                    'value': value
                },
                          headers={'Content-Type': 'application/json'})
            time.sleep(int(v.period) / 3)
