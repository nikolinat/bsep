from utils import read, state

messages = ['Front door is locked', 'No electricity', 'Front door is unlocked', 'Someone tried to unlock front door']


def main():
    devices = read('FRONT_DOOR')
    state(devices, messages)


if __name__ == '__main__':
    main()
