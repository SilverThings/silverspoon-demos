package org.openalt2015.demo.silverspoon.dht11_i2c;

import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.core.util.BulldogUtil;

import io.silverspoon.bulldog.core.io.bus.i2c.I2cBus;
import io.silverspoon.bulldog.core.io.bus.i2c.I2cConnection;

import java.io.IOException;
import java.util.List;

public class Dht11I2c {
   private Board board;
   private I2cBus i2c;
   private byte[] buffer;

   public Dht11I2c(){
      board = Platform.createBoard();
      List<I2cBus> l = board.getI2cBuses();
      i2c = board.getI2cBuses().get(0);
      buffer = new byte[2];
   }

   private void readValues() {
      final int address = 0x42;
      final I2cConnection connection = i2c.createI2cConnection(address);
      try {
         connection.readBytes(buffer);
         System.out.println("Temperature " + ((int) buffer[0]) + " °C, Humidity " + ((int) buffer[1]) + "%");
      } catch (IOException ioe){
         System.err.println("ERROR: Unable to read sensor values!");
         ioe.printStackTrace();
      }
   }

   public SensorData getSensorData() {
      readValues();
      return new SensorData().temperature(buffer[0]).humidity(buffer[1]);
   }
}
