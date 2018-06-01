#define USBCON
#include <Arduino.h>
#include "bluetooth.h"

BlueTooth *bt;
void readData(int *buf);
void sendData(int *data);
bool checkDifference(int *a1, int *a2, int num, int threshold);


void setup()
{
  Serial.begin(9600);
  bt = new BlueTooth(8, 9);
}

void loop()
{
  static int lastSend[5] = {0, 0, 0, 0, 0};
  static int dataBuf[5] = {0, 0, 0, 0, 0};
  static unsigned long lastSendTime = 0;

  readData(dataBuf);
  if (checkDifference(dataBuf, lastSend, 5, 100) || millis() - lastSendTime > 1000) {
    sendData(dataBuf);
    memcpy(lastSend, dataBuf, sizeof(int) * 5);
    lastSendTime = millis();
  }

}

void readData(int *buf) {
  buf[0] = analogRead(0);
  buf[1] = analogRead(1);
  buf[2] = analogRead(2);
  buf[3] = analogRead(3);
  buf[4] = analogRead(4);
}

void sendData(int *data) {
  char buf[25];
  sprintf(buf, "%d/%d/%d/%d/%d", data[0], data[1], data[2], data[3], data[4]);
  bt->send(buf);
}

bool checkDifference(int *a1, int *a2, int num, int threshold) {
  for (int i = 0; i < num; i++) {
    if (a1[i] - a2[i] > threshold || a2[i] - a1[i] > threshold)
      return true;
  }
  return false;
}