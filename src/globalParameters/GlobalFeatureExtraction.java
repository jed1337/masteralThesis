package globalParameters;

import featureExtraction.FeatureExtraction;

/**
 * Contains the relative paths to the arff files.
 * <p>
 * This class was created since we wanted to give
 * FeatureExtraction global access.
 * <br>
 * Global access was needed (though preferably not used)
 * since the code is too awful and many dependencies will be broken
 * if that was done.
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