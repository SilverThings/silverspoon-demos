// DHT11 over I2C
// Version 1.0
//
// Developed for Arduino Nano (rev. 3)
//
// DHT11 temperature/humidity sensor exposed over I2C bus.
// The HW project can be found at https://github.com/pmacik/electronics/tree/master/KiCad/dht11-i1c
//
// The DHT library can be found at https://github.com/adafruit/DHT-sensor-library

#include "DHT.h"
#include "Wire.h"

#define DHTPIN1 7       // Data pin is connected on D7
#define DHTTYPE DHT11   // DHT 11

const byte ADDRESS = 0x43;
DHT dht1(DHTPIN1, DHTTYPE);

byte values[2] = {0, 0};

void setup() {
  dht1.begin();

  Wire.begin(ADDRESS);
  Wire.onReceive(request);
  Wire.onRequest(response);
}

void response() {
  Wire.write(values, 2);
}

void request(int count) {
  while (Wire.available() > 0) {
    byte b = Wire.read();
  }
}

void loop() {
  long next = millis() + 1000;
  while (millis() < next) {}

  values[0] = (byte) dht1.readTemperature();
  values[1] = (byte) dht1.readHumidity();
}
