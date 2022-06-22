from utils import read, state

messages = ['Grejanje je ukljuceno', 'Nestalo je struje', 'Grejanje je iskljuceno']


def main():
    devices = read('HEATING')
    state(devices, messages)


if __name__ == '__main__':
    main()
