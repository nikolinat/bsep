import datetime
import random
import time

import requests

from utils import read

messages = ['Masina za sudove je upaljena.', 'Curi voda iz masine za sudove.', 'Nestalo je struje.', 'Nestalo je vode.',
            'Masina za sudove se iskljucila', 'Masina za sudove je zavrsila sa radom.', 'Sudovi su ostali u masini.']


def state(devices):
    for k, v in devices.items():
        message = datetime.datetime.utcnow().isoformat() + " " + v.id + " " + v.type + " " + random.choice(messages)
        requests.post('http://localhost:8444/api/v1/device/normal', data=message,
                      headers={'Content-Type': 'application/json'})
        time.sleep(int(v.period))


def main():
    devices = read('DISHWASHER')
    state(devices)


if __name__ == '__main__':
    main()
