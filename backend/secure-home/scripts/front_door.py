import datetime
import random
import time

import requests

from utils import read

messages = ['Ulazna vrata su zakljucana.', 'Nestalo je struje.', 'Ulazna vrata su otkljucana.',
            'Neko je pokusao da otkljuca vrata.']


def state(devices):
    for k, v in devices.items():
        message = datetime.datetime.utcnow().isoformat() + " " + v.id + " " + v.type + " " + random.choice(messages)
        requests.post('http://localhost:8444/api/v1/device/normal', data=message,
                      headers={'Content-Type': 'application/json'})
        time.sleep(int(v.period))


def main():
    devices = read('FRONT_DOOR')
    state(devices)


if __name__ == '__main__':
    main()
