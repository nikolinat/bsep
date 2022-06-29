from utils import read, state

messages = ['Air conditioning is on', 'No electricity', 'Air conditioning is off',
            'Air conditioning is in cooling mode', 'Air conditioning is in heating mode']


def main():
    devices = read('AIR_CONDITIONING')
    state(devices, messages)


if __name__ == '__main__':
    main()
