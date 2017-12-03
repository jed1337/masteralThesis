package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupExtraNoise;
import setupFiles.SetupFile;
import setupFiles.SetupNoise;
import setupFiles.SetupNormal;

public class ExtraNoise implements NoiseLevel {
   @Override
   public ArrayList<SetupFile> getNoiseLevel(final int count) throws IOException{
      ArrayList<SetupFile> setupFiles;

      setupFiles = new ArrayList<>();
      setupFiles.add(new SetupNoise(count/6));
      setupFiles.add(new SetupNormal(count/6));
      setupFiles.add(new SetupExtraNoise(count/6));
      
      return setupFiles;
   }
}
