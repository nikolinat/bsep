import datetime
import random
import time

import requests

from utils import read

messages = ['Pegla je upaljena.', 'Curi voda iz pegle.', 'Nestalo je struje.', 'Pegla se iskljucila.']


def state(devices):
    for k, v in devices.items():
        message = datetime.datetime.utcnow().isoformat() + " " + v.id + " " + v.type + " " + random.choice(messages)
        requests.post('http://localhost:8444/api/v1/device/normal', data=message,
                      headers={'Content-Type': 'application/json'})
        time.sleep(int(v.period))


def main():
    devices = read('IRON')
    state(devices)


if __name__ == '__main__':
    main()
