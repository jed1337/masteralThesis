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
}