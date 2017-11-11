package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainHighrate extends Train{
   public TrainHighrate() throws IOException {
      this(FileNameConstants.CNIS_HIGHRATE);
   }
   public TrainHighrate(FileNameConstants fileName) throws IOException {
      super(fileName);
   }

   @Override
   protected void removeNonMatchingClasses() {
      super.faa.removeNonMatchingClasses("service", "http");
   }

   @Override
   protected void keepXInstances() {
      super.faa.keepXInstances("isAttack", "tcpFlood", 1000);
      super.faa.keepXInstances("isAttack", "udpFlood", 1000);
      super.faa.keepXInstances("isAttack", "httpFlood", 1000);
   }
}