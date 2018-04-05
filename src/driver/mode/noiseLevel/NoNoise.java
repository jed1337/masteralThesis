package driver.mode.noiseLevel;

import constants.NoiseDatasetNames;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

/**
 * Null pattern implementation of NoiseLevel. <br>
 * It's implemented as a singleton since it's ok for many objects to have
 * reference to this since this class does nothing
 */
public final class NoNoise implements NoiseLevel{
   private static NoNoise instance = null;
   private NoNoise(){}
   
   /**
    * Create the NoNoise.instance if it doesn't exist
    * Use the existing NoNoise.instance otherwise
    * @return an instance of NoNoise
    */
   public static NoNoise getInstance(){
      if(NoNoise.instance == null){
         NoNoise.instance = new NoNoise();
      }
      System.err.println("Warning, getting 'No noise' for NoiseLevel");
      return NoNoise.instance;
   }

   /**
    * @return a new empty ArrayList<>()
    */
   @Override
   public List<PreprocessFile> getPreprocessFiles() {
      return Collections.unmodifiableList(new ArrayList<>());
   }
   
   /**
    * @return the NosieDatasetNames
    */
   @Override
   public NoiseDatasetNames getNoiseDatasetName(){
      return NoiseDatasetNames.NO_NOISE;
   }

   @Override
   public float getNoiseToAttackcRatio() {
      return 0.0f;
   }
}