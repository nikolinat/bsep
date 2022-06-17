from utils import read, state

messages = ['Roletne su spustene.', 'Nestalo je struje.', 'Roletne su podignute.']


def main():
    devices = read('BLINDS')
    state(devices, messages)


if __name__ == '__main__':
    main()
