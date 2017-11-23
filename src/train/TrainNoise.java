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
}