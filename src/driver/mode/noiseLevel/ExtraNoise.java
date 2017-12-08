package driver.mode.noiseLevel;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessExtraNoise;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public class ExtraNoise extends NoiseLevel{
   @Override
   public ArrayList<PreprocessFile> getPreprocessedFiles() throws IOException {
      super.pfAL.add(new PreprocessNoise());
      super.pfAL.add(new PreprocessExtraNoise());
      super.pfAL.add(new PreprocessNormal());
      return super.pfAL;
   }
}