package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainLowrate extends Train{
   public TrainLowrate() throws IOException{
      this(FileNameConstants.CNIS_LOWRATE);
   }
   public TrainLowrate(FileNameConstants fileName) throws IOException {
      super(fileName);
   }

   @Override
   protected void removeNonMatchingClasses() {
      super.faa.removeNonMatchingClasses("service", "http");
   }

   @Override
   protected void keepXInstances() {
      super.faa.keepXInstances("isAttack", "slowBody", 1000);
      super.faa.keepXInstances("isAttack", "slowHeaders", 1000);
      super.faa.keepXInstances("isAttack", "slowRead", 1000);
   }
}