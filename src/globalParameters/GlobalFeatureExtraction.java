package globalParameters;

import featureExtraction.FeatureExtraction;

/**
 * This class was created since we wanted to give
 * FeatureExtraction global access. <br>
 * 
 * An instance of FeatureExtraction wasn't just passed around
 * since the code is too awful and many dependencies will be broken.
 */
public class GlobalFeatureExtraction {
   private static FeatureExtraction instance=null;
   
   public static void setInstance(FeatureExtraction fe){
      if(GlobalFeatureExtraction.instance!=null){
         throw new IllegalArgumentException("The instance has already been set");
      }
      GlobalFeatureExtraction.instance = fe;
   }
   
   public static FeatureExtraction getInstance(){
      return GlobalFeatureExtraction.instance;
   }

   /**
    * A private constructor so that no class except this one 
    * can create an instance
    */
   private GlobalFeatureExtraction() {}
}