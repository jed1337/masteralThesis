package preprocessFiles.noise;

import constants.PreprocessFileName;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseNormal extends PreprocessNoiseFile{
   public PreprocessNoiseNormal() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNoiseNormalPath(),
         PreprocessFileName.NOISE_NORMAL);
   }
}