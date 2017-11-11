package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainNoise extends Train{
   public TrainNoise() throws IOException {
      this(FileNameConstants.CNIS_NOISE);
   }
   
   public TrainNoise(FileNameConstants fileName) throws IOException {
      super(fileName);
   }

   @Override
   protected void removeNonMatchingClasses() {
      super.faa.removeNonMatchingClasses("service", "http");
   }

   @Override
   protected void keepXInstances() {
      super.faa.keepXInstances("isAttack", "normal", 1500);
   }
}