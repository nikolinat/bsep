import datetime
import random
import time

import requests

from utils import read

messages = ['Ves masina je upaljena.', 'Curi voda iz ves masine.', 'Nestalo je struje.', 'Nestalo je vode.',
            'Ves masina se iskljucila', 'Ves masina je zavrsila sa radom.', 'Ves je ostao u masini za pranje vesa.']


def state(devices):
    for k, v in devices.items():
        message = datetime.datetime.utcnow().isoformat() + " " + v.id + " " + v.type + " " + random.choice(messages)
        requests.post('http://localhost:8444/api/v1/device/normal', data=message,
                      headers={'Content-Type': 'application/json'})
        time.sleep(int(v.period))


def main():
    devices = read('WASHING_MACHINE')
    state(devices)


if __name__ == '__main__':
    main()
