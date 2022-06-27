from utils import read, state

messages = [ 'Curi voda iz pegle']


def main():
    devices = read('IRON')
    state(devices, messages)


if __name__ == '__main__':
    main()
