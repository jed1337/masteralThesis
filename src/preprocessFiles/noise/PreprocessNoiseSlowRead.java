package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseSlowRead extends PreprocessNoiseFile{
   public PreprocessNoiseSlowRead() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseSlowReadPath());
   }
}