package io.silverspoon.demos.dht11_i2c;

public class SensorException extends Exception {

   public SensorException(final String message, final Throwable cause) {
      super(message, cause);
   }

   public SensorException(final Throwable cause) {
      super(cause);
   }

   public SensorException(final String message) {
      super(message);
   }
}
