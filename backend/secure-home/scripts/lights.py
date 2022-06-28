from utils import read, state

messages = ['The light is on', 'No electricity', 'The light is off']


def main():
    devices = read('LIGHTS')
    state(devices, messages)


if __name__ == '__main__':
    main()
