package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseUDPFlood extends PreprocessNoiseFile{
   public PreprocessNoiseUDPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseUDPFloodPath());
   }
}