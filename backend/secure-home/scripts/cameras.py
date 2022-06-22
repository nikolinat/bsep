from utils import read, state

messages = ['Kamere su ukljucene', 'Nestalo je struje', 'Kamere su iskljucene', 'Uocen nepoznat objekat']


def main():
    devices = read('CAMERAS')
    state(devices, messages)


if __name__ == '__main__':
    main()
