from utils import read, state

messages = ['Klima je ukljucena', 'Nestalo je struje', 'Klima je iskljucena', 'Klima je ukljucena u rezimu hladjenja',
            'Klima je ukljucena u rezimu grejanja']


def main():
    devices = read('AIR_CONDITIONING')
    state(devices, messages)


if __name__ == '__main__':
    main()
