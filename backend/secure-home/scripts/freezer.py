from utils import read, state

messages = ['Freezer is off', 'Freezer is on', 'Water is leaking from the freezer', 'No electricity', 'No water',
            'Freezer is open']


def main():
    devices = read('FREEZER')
    state(devices, messages)


if __name__ == '__main__':
    main()
