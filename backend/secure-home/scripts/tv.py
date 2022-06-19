from utils import read, state

messages = ['Televizor je upaljen.', 'Nestalo je struje.']


def main():
    devices = read('TV')
    state(devices, messages)


if __name__ == '__main__':
    main()
