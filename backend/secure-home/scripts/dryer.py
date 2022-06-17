import datetime
import random
import time

import requests

from utils import read

messages = ['Masina za susenje vesa je upaljena.', 'Curi voda iz masine za susenje vesa.', 'Nestalo je struje.',
            'Nestalo je vode.', 'Masina za susenje vesa se iskljucila', 'Masina za susenje vesa je zavrsila sa radom.',
            'Ves je ostao u masini za susenje vesa.']


def state(devices):
    for k, v in devices.items():
        message = datetime.datetime.utcnow().isoformat() + " " + v.id + " " + v.type + " " + random.choice(messages)
        requests.post('http://localhost:8444/api/v1/device/normal', data=message,
                      headers={'Content-Type': 'application/json'})
        time.sleep(int(v.period))


def main():
    devices = read('DRYER')
    state(devices)


if __name__ == '__main__':
    main()
