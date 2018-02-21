package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseHttpFlood extends PreprocessNoiseFile{
   public PreprocessNoiseHttpFlood() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseHTTPFloodPath());
   }
}