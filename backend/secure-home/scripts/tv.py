from utils import read, state

messages = ['Tv is on', 'No electricity', 'Tv is off']


def main():
    devices = read('TV')
    state(devices, messages)


if __name__ == '__main__':
    main()
