package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseTCPFlood extends PreprocessNoiseFile{
   public PreprocessNoiseTCPFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseTCPFloodPath());
   }
}