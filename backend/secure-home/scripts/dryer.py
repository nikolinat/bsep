from utils import read, state

messages = ['Dryer is on', 'Water is leaking from the dryer', 'No electricity', 'No water',
            'Dryer shut down', 'Dryer is done', 'Laundry is in the dryer']


def main():
    devices = read('DRYER')
    state(devices, messages)


if __name__ == '__main__':
    main()
