from utils import read, state

messages = ['Kapija je zakljucana', 'Nestalo je struje', 'Kapija je otkljucana',
            'Neko je pokusao da otkljuca kapiju']


def main():
    devices = read('GATE')
    state(devices, messages)


if __name__ == '__main__':
    main()
