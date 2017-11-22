package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainLowrate extends Train{
   public TrainLowrate(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.CNIS_LOWRATE);
   }

   public TrainLowrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"slowBody", "slowHeaders", "slowRead"});
   }

//   @Override
//   protected void keepXInstances() {
//      for (String attackType : super.attackTypes) {
//         super.faa.keepXInstances("isAttack", attackType, super.getDivider());
//      }
////      super.faa.keepXInstances("isAttack", "slowBody", 500);
////      super.faa.keepXInstances("isAttack", "slowHeaders", 500);
////      super.faa.keepXInstances("isAttack", "slowRead", 500);
//      // super.faa.keepXInstances("isAttack", "slowBody", 1000);
//      // super.faa.keepXInstances("isAttack", "slowHeaders", 1000);
//      // super.faa.keepXInstances("isAttack", "slowRead", 1000);
//   }
}