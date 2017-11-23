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
}