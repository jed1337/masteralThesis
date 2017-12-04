package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessExtraNoise;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public class ExtraNoise implements NoiseLevel {
   @Override
   public ArrayList<PreprocessFile> getNoiseLevel(final int count) throws IOException{
      ArrayList<PreprocessFile> setupFiles;

      setupFiles = new ArrayList<>();
      setupFiles.add(new PreprocessNoise(count/6));
      setupFiles.add(new PreprocessNormal(count/6));
      setupFiles.add(new PreprocessExtraNoise(count/6));
      
      return setupFiles;
   }
}
