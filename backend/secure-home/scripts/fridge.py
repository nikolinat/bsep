from utils import read, state

messages = ['Fridge is off', 'Fridge is on', 'Water is leaking from the fridge', 'No electricity', 'No water',
            'Fridge is open']


def main():
    devices = read('FRIDGE')
    state(devices, messages)


if __name__ == '__main__':
    main()
