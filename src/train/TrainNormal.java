package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainNormal extends Train{
   public TrainNormal() throws IOException{
      this(FileNameConstants.KDD_TRAIN);
   }
   
   public TrainNormal(String fileName) throws IOException {
      super(fileName);
   }

   @Override
   protected void removeNonMatchingClasses() {
      super.faa.removeNonMatchingClasses("isAttack", "normal");
//      super.faa.removeNonMatchingClasses("isAttack", "normal", "neptune");
      super.faa.removeNonMatchingClasses("service", "http");
   }

   @Override
   protected void keepXInstances() {
      super.faa.keepXInstances("isAttack", "normal", 3000);
//      super.faa.keepXInstances("isAttack", "normal", 1500);
//      super.faa.keepXInstances("isAttack", "neptune", 1500);
   }
}