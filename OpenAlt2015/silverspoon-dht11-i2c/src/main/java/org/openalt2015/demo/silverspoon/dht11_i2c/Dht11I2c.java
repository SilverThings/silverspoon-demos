package org.openalt2015.demo.silverspoon.dht11_i2c;

import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.core.util.BulldogUtil;

import io.silverspoon.bulldog.core.io.bus.i2c.I2cBus;
import io.silverspoon.bulldog.core.io.bus.i2c.I2cConnection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * A Bean to read DHT11-I2C sensor data over I2C bus.
 *
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class Dht11I2c {
   private static final Object lock = new Object();
   private static Logger log = Logger.getLogger(Dht11I2c.class);
   private static int ADDRESS = 0x42;
   private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   private Board board;
   private I2cBus i2c;
   private String sensorName;

   public Dht11I2c() {
      board = Platform.createBoard();
      List<I2cBus> l = board.getI2cBuses();
      i2c = board.getI2cBuses().get(0);
      sensorName = "DHT11-I2C:" + i2c.getName();
      if(log.isInfoEnabled()){
         log.info("Initialized " + Dht11I2c.class.getName() + " bean for sensor '" + sensorName + "'.");
      }
   }

   private byte[] readValues() throws SensorException {
      final byte[] buffer = new byte[2];
      boolean invalidData = false;
      synchronized(lock){
         final I2cConnection connection = i2c.createI2cConnection(ADDRESS);
         try {
            connection.readBytes(buffer);
            if(buffer[0] == 0 || buffer[1] == 0){
               invalidData = true;
            }
            if(log.isDebugEnabled()){
               log.debug("Temperature " + ((int) buffer[0]) + " °C, Humidity " + ((int) buffer[1]) + "%");
            }
         } catch (IOException ioe){
            ioe.printStackTrace();
            invalidData = true;
         }
         if(invalidData){
            throw new SensorException("Unable to read sensor values!");
         }
      }
      return buffer;
   }

   public List<String> getTemperature() throws SensorException {
      final List<String> list = new ArrayList<>();
      SensorData sd = getSensorData();
      list.add(sd.sensorName());
      list.add(String.valueOf(sd.temperature()));
      return list;
   }

   public List<String> getHumidity() throws SensorException {
      final List<String> list = new ArrayList<>();
      SensorData sd = getSensorData();
      list.add(sd.sensorName());
      list.add(String.valueOf(sd.humidity()));
      return list;
   }

   public SensorData getSensorData() throws SensorException {
      final byte[] buffer = readValues();
      return new SensorData()
                  .temperature(buffer[0])
                  .humidity(buffer[1])
                  .sensorName(sensorName)
                  .timestamp(timestampFormat.format(new Date()));
   }
}
