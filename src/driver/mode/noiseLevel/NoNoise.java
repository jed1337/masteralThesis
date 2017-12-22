package driver.mode.noiseLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class NoNoise implements NoiseLevel{
   private static NoNoise instance =null;
   private NoNoise(){}
   
   public static NoNoise getInstance(){
      if(NoNoise.instance == null){
         NoNoise.instance = new NoNoise();
      }
      System.err.println("Warning, getting 'No noise' for NoiseLevel");
      return NoNoise.instance;
   }
   
   @Override
   public List<PreprocessFile> getPreprocessedFiles() {
      final ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      return Collections.unmodifiableList(pfAL);
   }
   
   @Override
   public float getNoiseLevelFloat(){
      return 0.0f;
   }

   @Override
   public String getNoiseLevelString() {
      return "No noise";
   }
}