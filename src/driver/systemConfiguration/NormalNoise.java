package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupFile;
import setupFiles.SetupNoise;
import setupFiles.SetupNormal;

public class NormalNoise implements NoiseLevel{
   @Override
   public ArrayList<SetupFile> getNoiseLevel(final int count) throws IOException{
      ArrayList<SetupFile> setupFiles;

      setupFiles = new ArrayList<>();
      setupFiles.add(new SetupNoise(count/4));
      setupFiles.add(new SetupNormal(count/4));
      
      return setupFiles;
   }
}