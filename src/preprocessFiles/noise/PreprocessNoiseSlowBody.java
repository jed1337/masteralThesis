package preprocessFiles.noise;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;

public class PreprocessNoiseSlowBody extends PreprocessNoiseFile{
   public PreprocessNoiseSlowBody() throws IOException {
      super(GlobalFeatureExtraction.getInstance().getNoiseSlowBodyPath());
   }
}