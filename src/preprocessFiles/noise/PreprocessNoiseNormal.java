package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseNormal extends PreprocessNoiseFile{
   public PreprocessNoiseNormal() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseNormalPath());
   }
}