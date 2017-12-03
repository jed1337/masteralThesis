package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupFile;
import setupFiles.SetupHighrate;
import setupFiles.SetupLowrate;

public abstract class SystemConfiguration {
   protected final ArrayList<SetupFile> setupFiles;
   
   private final int count;
   
   protected SystemConfiguration(NoiseLevel nl, int count) throws IOException{
      this.count = count;
      
      setupFiles = nl.getNoiseLevel(this.count);
      setupFiles.addAll(getHL());
   }

   protected final ArrayList<SetupFile> getHL() throws IOException {
      ArrayList<SetupFile> sf = new ArrayList<>();
      sf.add(new SetupHighrate(this.count/4));
      sf.add(new SetupLowrate (this.count/4));
      return sf;
   }
   
   public abstract void execute(String folderPath) throws IOException, Exception;
}