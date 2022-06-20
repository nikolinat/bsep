from utils import read, state

messages = ['Svetlo je upaljeno', 'Nestalo je struje', 'Svetlo je ugaseno']


def main():
    devices = read('LIGHTS')
    state(devices, messages)


if __name__ == '__main__':
    main()
