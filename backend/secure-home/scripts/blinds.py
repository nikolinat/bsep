from utils import read, state

messages = ['Blinds are lowered', 'No electricity', 'Blinds are raised']


def main():
    devices = read('BLINDS')
    state(devices, messages)


if __name__ == '__main__':
    main()
