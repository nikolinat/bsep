from utils import read, state

messages = ['Ulazna vrata su zakljucana', 'Nestalo je struje', 'Ulazna vrata su otkljucana',
            'Neko je pokusao da otkljuca vrata']


def main():
    devices = read('FRONT_DOOR')
    state(devices, messages)


if __name__ == '__main__':
    main()
