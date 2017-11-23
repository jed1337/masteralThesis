package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainExtraNoise extends Train{
   public TrainExtraNoise(int instancesCount) throws IOException {
      this(instancesCount, FileNameConstants.CNIS_EXTRA_NOISE);
   }
   
   public TrainExtraNoise(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}