package io.silverspoon.demos.dht11_i2c;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class HumidityProcessor implements Processor {
   @Override
   public void process(final Exchange exchange) throws Exception {
      final Message in = exchange.getIn();
      final SensorData sensorData = (SensorData) in.getBody();
      final List<String> list = new ArrayList<>();
      list.add(sensorData.sensorName());
      list.add(String.valueOf(sensorData.humidity()));
      in.setBody(list);
   }
}
