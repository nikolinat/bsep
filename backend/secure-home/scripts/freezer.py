from utils import read, state

messages = ['Zamrzivac se iskljucio.', 'Curi voda iz zamrzivaca.', 'Nestalo je struje.', 'Nestalo je vode.',
            'Zamrzivac je otvoren.']


def main():
    devices = read('FREEZER')
    state(devices, messages)


if __name__ == '__main__':
    main()
