package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainNeptune extends Train{
   public TrainNeptune(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public TrainNeptune(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"neptune"});
   }

//   @Override
//   protected void removeNonMatchingClasses() {
//      super.faa.removeNonMatchingClasses("isAttack", "neptune");
//      super.faa.removeNonMatchingClasses("service", "http");
//   }
//
//   @Override
//   protected void keepXInstances() {
//      super.faa.keepXInstances("isAttack", "neptune", super.instancesCount);
////      super.faa.keepXInstances("isAttack", "neptune", 1500);
//   }
}