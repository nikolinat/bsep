from utils import read, state

messages = ['Cooker is on', 'No electricity', 'Cooker is off']


def main():
    devices = read('COOKER')
    state(devices, messages)


if __name__ == '__main__':
    main()
