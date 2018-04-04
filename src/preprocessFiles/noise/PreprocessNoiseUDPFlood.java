package preprocessFiles.noise;

import constants.PreprocessFileName;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseUDPFlood extends PreprocessNoiseFile{
   public PreprocessNoiseUDPFlood() throws IOException {
      super(
         GlobalFeatureExtraction.getInstance().getNoiseUDPFloodPath(),
         PreprocessFileName.NOISE_UDP_FLOOD);
   }
}