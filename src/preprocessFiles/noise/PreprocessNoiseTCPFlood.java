package preprocessFiles.noise;

import constants.PreprocessFileName;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseTCPFlood extends PreprocessNoiseFile{
   public PreprocessNoiseTCPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNoiseTCPFloodPath(),
         PreprocessFileName.NOISE_TCP_FLOOD);
   }
}