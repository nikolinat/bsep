from utils import read, state

messages = ['Sporet je upaljen', 'Nestalo je struje', 'Ostao je ukljucen gas']


def main():
    devices = read('COOKER')
    state(devices, messages)


if __name__ == '__main__':
    main()
