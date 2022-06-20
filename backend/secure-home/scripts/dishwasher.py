from utils import read, state

messages = ['Masina za sudove je upaljena', 'Curi voda iz masine za sudove', 'Nestalo je struje', 'Nestalo je vode',
            'Masina za sudove se iskljucila', 'Masina za sudove je zavrsila sa radom', 'Sudovi su ostali u masini']


def main():
    devices = read('DISHWASHER')
    state(devices, messages)


if __name__ == '__main__':
    main()
