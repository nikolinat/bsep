from utils import read, state

messages = ['Frizider se iskljucio.', 'Curi voda iz frizidera.', 'Nestalo je struje.', 'Nestalo je vode.',
            'Frizider je otvoren.']


def main():
    devices = read('FRIDGE')
    state(devices, messages)


if __name__ == '__main__':
    main()
