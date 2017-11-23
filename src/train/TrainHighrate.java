package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainHighrate extends Train{
   public TrainHighrate(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_HIGHRATE);
   }

   public TrainHighrate(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"tcpFlood", "udpFlood", "httpFlood"});
   }
}