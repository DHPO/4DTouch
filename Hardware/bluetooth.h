#ifndef __BLUETOOTH__
#define __BLUETOOTH__
#include <Arduino.h>
#include <SoftwareSerial.h>

/* 适用型号 HC-06 */
class BlueTooth
{
  private:
    SoftwareSerial serial;
    bool ATstatus();
    bool checkOK();

  public:
    BlueTooth(int rec, int send, int bps = 9600);
    void send(const char *);
    void send(int num, int base = DEC);
    void sendFromSerial();
    void sendRaw();
    int receive(char *buf, int maxbuf); // receive message to buffer
    int check();                        // call serial.available()
    void clear();
    bool status();
    bool setName(const char *name);
    bool setPin(const char *pin);
};

#endif