package org.openalt2015.demo.silverspoon.dht11_i2c;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class SensorDataProcessor implements Processor {
   private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private static final String SENSOR_NAME = System.getProperty("sensor.name", "DHT11_I2C");

   @Override
   public void process(final Exchange exchange) throws Exception {

      final Message in = exchange.getIn();
      final String body = (String) in.getBody();
      final SensorData sensorData = new SensorData();
      sensorData
            .sensorName(SENSOR_NAME)
            .temperature(Byte.decode("0x" + body.substring(0, 2)))
            .humidity(Byte.decode("0x" + body.substring(2, 4)))
            .timestamp(TIMESTAMP_FORMAT.format(new Date()));
      in.setBody(sensorData);
   }
}
