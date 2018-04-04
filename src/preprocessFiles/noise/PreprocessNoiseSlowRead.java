package preprocessFiles.noise;

import constants.PreprocessFileName;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseSlowRead extends PreprocessNoiseFile{
   public PreprocessNoiseSlowRead() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNoiseSlowReadPath(),
         PreprocessFileName.NOISE_SLOW_READ);
   }
}