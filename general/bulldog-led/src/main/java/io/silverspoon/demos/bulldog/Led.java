package io.silverspoon.demos.bulldog;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.core.util.BulldogUtil;
import io.silverspoon.bulldog.raspberrypi.RaspiNames;

public class Led {
   public static void main(String[] args) {
      //Detect the board we are running on
      Board board = Platform.createBoard();

      //Set up a digital output
      DigitalOutput output = board.getPin(RaspiNames.P1_11).as(DigitalOutput.class);

      // Blink the LED
      for(int i = 0; i < 5; i++){
         System.out.println("1");
         output.high();
         BulldogUtil.sleepMs(1000);
         System.out.println("0");
         output.low();
         BulldogUtil.sleepMs(1000);
      }
   }
}
