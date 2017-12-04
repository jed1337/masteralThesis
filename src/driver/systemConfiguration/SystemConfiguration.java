package driver.systemConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHighrate;
import preprocessFiles.PreprocessLowrate;
import weka.classifiers.Classifier;

public abstract class SystemConfiguration {
   protected final ArrayList<PreprocessFile> setupFiles;
   protected final Classifier featureSelector;
   
   private final int count;
   
   protected SystemConfiguration(int count, NoiseLevel nl, Classifier featureSelector) throws IOException{
      this.count = count;
      
      this.setupFiles = nl.getNoiseLevel(this.count);
      this.setupFiles.addAll(getHL());
      
      this.featureSelector = featureSelector;
   }

   protected final ArrayList<PreprocessFile> getHL() throws IOException {
      ArrayList<PreprocessFile> sf = new ArrayList<>();
      sf.add(new PreprocessHighrate(this.count/4));
      sf.add(new PreprocessLowrate (this.count/4));
      return sf;
   }
   
   public abstract void execute(String folderPath) throws IOException, Exception;
}