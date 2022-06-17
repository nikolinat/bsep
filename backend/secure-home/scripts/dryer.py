from utils import read, state

messages = ['Masina za susenje vesa je upaljena.', 'Curi voda iz masine za susenje vesa.', 'Nestalo je struje.',
            'Nestalo je vode.', 'Masina za susenje vesa se iskljucila', 'Masina za susenje vesa je zavrsila sa radom.',
            'Ves je ostao u masini za susenje vesa.']


def main():
    devices = read('DRYER')
    state(devices, messages)


if __name__ == '__main__':
    main()
