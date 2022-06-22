from utils import read, state

messages = ['Ves masina je upaljena', 'Curi voda iz ves masine', 'Nestalo je struje', 'Nestalo je vode',
            'Ves masina se iskljucila', 'Ves masina je zavrsila sa radom', 'Ves je ostao u masini za pranje vesa']


def main():
    devices = read('WASHING_MACHINE')
    state(devices, messages)


if __name__ == '__main__':
    main()
