package train;

import constants.FileNameConstants;
import java.io.IOException;

public class TrainNormal extends Train{
   public TrainNormal(int instancesCount) throws IOException{
      this(instancesCount, FileNameConstants.KDD_TRAIN);
   }
   
   public TrainNormal(int instancesCount, String fileName) throws IOException {
      super(instancesCount, fileName, new String[]{"normal"});
   }
}