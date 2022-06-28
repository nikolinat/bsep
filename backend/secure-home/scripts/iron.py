from utils import read, state

messages = ['Iron is on', 'Iron is off', 'No electricity', 'Leaking water from iron', 'Iron shut down']


def main():
    devices = read('IRON')
    state(devices, messages)


if __name__ == '__main__':
    main()
