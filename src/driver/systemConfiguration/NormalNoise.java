package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNoise;
import preprocessFiles.PreprocessNormal;

public class NormalNoise implements NoiseLevel{
   @Override
   public ArrayList<PreprocessFile> getNoiseLevel(final int count) throws IOException{
      ArrayList<PreprocessFile> setupFiles;

      setupFiles = new ArrayList<>();
      setupFiles.add(new PreprocessNoise(count/4));
      setupFiles.add(new PreprocessNormal(count/4));
      
      return setupFiles;
   }
}