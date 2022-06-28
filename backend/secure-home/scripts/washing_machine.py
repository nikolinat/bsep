from utils import read, state

messages = ['Washing machine is on', 'Leaking water from washing machine', 'No electricity', 'No water',
            'Washing machine shut down', 'Washing machine is done', 'Laundry is in the washing machine']


def main():
    devices = read('WASHING_MACHINE')
    state(devices, messages)


if __name__ == '__main__':
    main()
