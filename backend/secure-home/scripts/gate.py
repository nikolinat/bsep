from utils import read, state

messages = ['Gate is locked', 'No electricity', 'Gate is unlocked', 'Someone tried to unlock the gate']


def main():
    devices = read('GATE')
    state(devices, messages)


if __name__ == '__main__':
    main()
