from utils import read, state

messages = ['Heating is on', 'No electricity', 'Heating is off']


def main():
    devices = read('HEATING')
    state(devices, messages)


if __name__ == '__main__':
    main()
