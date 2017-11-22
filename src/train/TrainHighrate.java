package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainHighrate extends TrainMultiple{
   public TrainHighrate(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_HIGHRATE);
   }

   public TrainHighrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"tcpFlood", "udpFlood", "httpFlood"});
   }

//    @Override
//    protected void keepXInstances() {
//       for (String attackType : super.attackTypes) {
//          super.faa.keepXInstances("isAttack", attackType, super.getDivider());
//       }
// //         super.faa.keepXInstances("isAttack", "tcpFlood", 1000);
// //         super.faa.keepXInstances("isAttack", "udpFlood", 1000);
// //         super.faa.keepXInstances("isAttack", "httpFlood", 1000);
//    }
}