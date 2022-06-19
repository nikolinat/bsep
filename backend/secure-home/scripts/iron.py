from utils import read, state

messages = ['Pegla je upaljena.', 'Curi voda iz pegle.', 'Nestalo je struje.', 'Pegla se iskljucila.']


def main():
    devices = read('IRON')
    state(devices, messages)


if __name__ == '__main__':
    main()
