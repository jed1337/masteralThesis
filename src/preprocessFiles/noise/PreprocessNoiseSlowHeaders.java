package preprocessFiles.noise;

import constants.PreprocessFileName;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseSlowHeaders extends PreprocessNoiseFile{
   public PreprocessNoiseSlowHeaders() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNoiseSlowHeadersPath(),
         PreprocessFileName.NOISE_SLOW_HEADERS);
   }
}