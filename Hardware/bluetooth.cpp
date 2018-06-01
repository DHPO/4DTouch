#define USBCON
#include "bluetooth.h"

BlueTooth::BlueTooth(int rec, int send, int bps) : serial(rec, send)
{
    serial.begin(bps);
}

void BlueTooth::send(const char *message)
{
    const char *ch = message;
    while (*ch)
    {
        serial.print(*ch);
        ch++;
    }
    serial.print((char)0xD);
    serial.print((char)0x13);
}

void BlueTooth::send(int message, int base)
{
    serial.print(message, base);
    serial.print((char)0xD);
    serial.print((char)0x13);
}

void BlueTooth::sendFromSerial()
{
    char val;
    while (Serial.available())
    {
        val = Serial.read();
        serial.print(val);
    }
    serial.print((char)0x13);
}

void BlueTooth::sendRaw()
{
    char val;
    while (Serial.available())
    {
        val = Serial.read();
        serial.print(val);
    }
    serial.println("");
}

int BlueTooth::receive(char *buf, int maxbuf)
{
    int count = 0;
    while (serial.available() && count < maxbuf)
    {
        if (!serial.available())
            continue;
        *buf = serial.read();
        buf++;
        count++;
    }
    return count;
}

int BlueTooth::check()
{
    return serial.available();
}

void BlueTooth::clear()
{
    while (serial.available())
        serial.read();
}

bool BlueTooth::checkOK()
{
    char buf[64];
    int cnt;

    unsigned int start = millis();
    while (!serial.available() || millis() - start < 1000)
        ;

    if ((cnt = receive(buf, 63)) && buf[0] == 'O' && buf[1] == 'K')
    {
        clear();
        return true;
    }
    else
    {
        clear();
        return false;
    }
}

bool BlueTooth::ATstatus()
{
    clear();
    serial.println("AT");
    return checkOK();
}

bool BlueTooth::status()
{
    return !ATstatus();
}

bool BlueTooth::setName(const char *name)
{
    clear();
    if (!ATstatus())
        return false;

    serial.print("AT+NAME");
    serial.println(name);

    return true;
}

bool BlueTooth::setPin(const char *pin)
{
    clear();
    if (!ATstatus())
        return false;

    serial.print("AT+PIN");
    serial.println(pin);

    return true;
}