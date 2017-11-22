package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainNoise extends Train{
   public TrainNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_NOISE);
   }
   
   public TrainNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }

//   @Override
//   protected void removeNonMatchingClasses() {
//      super.faa.removeNonMatchingClasses("isAttack", "normal");
//      super.faa.removeNonMatchingClasses("service", "http");
//   }
//
//   @Override
//   protected void keepXInstances() {
//      super.faa.keepXInstances("isAttack", "normal", super.instancesCount);
////      super.faa.keepXInstances("isAttack", "normal", 3000);
////      super.faa.keepXInstances("isAttack", "normal", 1500);
////      super.faa.keepXInstances("isAttack", "normal", 750);
//   }
}