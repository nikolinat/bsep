from utils import read, state

messages = ['Dishwasher is on', 'Water is leaking from the dishwasher', 'No electricity', 'No water',
            'Dishwasher is off', 'Dishwasher shut down', 'Dishwasher is done', 'Dishes are in the dishwasher']


def main():
    devices = read('DISHWASHER')
    state(devices, messages)


if __name__ == '__main__':
    main()
