from utils import read, state

messages = ['Cameras are on', 'No electricity', 'Cameras are off', 'Spotted unknown object']


def main():
    devices = read('CAMERAS')
    state(devices, messages)


if __name__ == '__main__':
    main()
